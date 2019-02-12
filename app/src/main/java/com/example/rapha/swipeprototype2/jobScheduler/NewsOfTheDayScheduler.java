package com.example.rapha.swipeprototype2.jobScheduler;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.NewsOfTheDayFragment;
import com.example.rapha.swipeprototype2.activities.viewElements.DimensionService;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.api.NewsApiUtils;
import com.example.rapha.swipeprototype2.http.HttpRequest;
import com.example.rapha.swipeprototype2.http.IHttpRequester;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.newsCategories.QueryWordTransformation;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.time.ApiRequestTimeService;
import com.example.rapha.swipeprototype2.utils.ListService;
import com.example.rapha.swipeprototype2.utils.Logging;

import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NewsOfTheDayScheduler extends JobService implements IHttpRequester {

    String TAG = "scheduler";
    NewsArticleDbService newsArticleDbService;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "job started");
        newsArticleDbService = NewsArticleDbService.getInstance(getApplication());
        KeyWordDbService keyWordDbService = KeyWordDbService.getInstance(getApplication());
        Observer observer = getObserverToRequestArticles(keyWordDbService, jobParameters);

        keyWordDbService.getAllLikedKeyWords().observeForever(observer);

        return true;
    }

    private Observer getObserverToRequestArticles(KeyWordDbService keyWordDbService, JobParameters jobParameters){
        HttpRequest httpRequest = new HttpRequest(NewsOfTheDayScheduler.this, 0);
        Observer<List<KeyWordRoomModel>> observer = new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topicsToLookFor) {
                if(topicsToLookFor.size() >= 5){
                    Log.d(TAG, "in onchanged");
                    for(int i = 0; i < topicsToLookFor.size(); i++) {
                        String[] keyWords = new QueryWordTransformation().getKeyWordsFromTopics(topicsToLookFor.get(i));
                        try {
                            ApiService.getArticlesNewsApiByKeyWords(
                                    httpRequest, keyWords, LanguageSettingsService.INDEX_ENGLISH
                            );
                            jobFinished(jobParameters, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            jobFinished(jobParameters, true);
                        }
                    }
                    keyWordDbService.getAllLikedKeyWords().removeObserver(this);
                }
            }
        };
        return observer;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "job cancelled");
        return true;
    }

    @Override
    public void httpResultCallback(JSONObject newsArticleJson) {
        Log.d(TAG, "reached httpResultCallback");
        LinkedList<NewsArticle> articlesForKeyword = new LinkedList<>();
        try {
            articlesForKeyword = NewsApiUtils.jsonToNewsArticleArray(newsArticleJson, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(articlesForKeyword.size() > 0){
            notification();
            setDateArticlesLoaded();
            int entryToAdd = 0;
            NewsArticle articleToAdd = articlesForKeyword.get(entryToAdd);
            Log.d(TAG, "adds this article to db: " + articlesForKeyword.get(entryToAdd));
            storeArticleInDatabase(articleToAdd);
        }


    }


    private void setDateArticlesLoaded(){
        ApiRequestTimeService.saveLastLoadedDefault(
                getApplicationContext(),
                new Date(),
                ApiRequestTimeService.TIME_OF_RELAOD_DAILY);
    }

    private void storeArticleInDatabase(NewsArticle newsArticle){
        NewsArticleRoomModel insert = newsArticleDbService.createNewsArticleRoomModelToInsert(
                newsArticle
        );
        insert.articleType = NewsArticleRoomModel.NEWS_OF_THE_DAY;
        newsArticleDbService.insert(insert);
    }

    private void notification(){
        int NOTIFICATION_ID = 233;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";

//                  Channel
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription("Description");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if(alarmSound == null){
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if(alarmSound == null){
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
//              Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), "my_channel_01")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText("Just requested new articles")
                .setTicker("Ticker")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
