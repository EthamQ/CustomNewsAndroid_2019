package com.example.rapha.swipeprototype2.jobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.NewsOfTheDayFragment;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.api.NewsApiUtils;
import com.example.rapha.swipeprototype2.http.HttpRequest;
import com.example.rapha.swipeprototype2.http.HttpRequestInfo;
import com.example.rapha.swipeprototype2.http.IHttpRequester;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.newsCategories.QueryWordTransformation;
import com.example.rapha.swipeprototype2.notifications.NewsOfTheDayNotificationService;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.time.ApiRequestTimeService;

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
        HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
        HttpRequest httpRequest = new HttpRequest(NewsOfTheDayScheduler.this, httpRequestInfo);
        Observer<List<KeyWordRoomModel>> observer = new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topicsToLookFor) {
                if(topicsToLookFor.size() >= NewsOfTheDayFragment.ARTICLE_MINIMUM){
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
    public void httpResultCallback(HttpRequestInfo info) {
        Log.d(TAG, "reached httpResultCallback");
        LinkedList<NewsArticle> articlesForKeyword = new LinkedList<>();
        JSONObject newsArticleJson = (JSONObject) info.getData();
        try {
            articlesForKeyword = NewsApiUtils.jsonToNewsArticleArray(newsArticleJson, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(articlesForKeyword.size() > 0){
            NewsOfTheDayNotificationService.sendNotificationLoadedDailyNews(this);
            setDateArticlesLoaded();
            int entryToAdd = 0;
            NewsArticle articleToAdd = articlesForKeyword.get(entryToAdd);
            Log.d(TAG, "adds this article to db: " + articlesForKeyword.get(entryToAdd));
            newsArticleDbService.insert(articleToAdd);
        }
    }

    private void setDateArticlesLoaded(){
        ApiRequestTimeService.saveLastLoadedDefault(
                getApplicationContext(),
                new Date(),
                ApiRequestTimeService.TIME_OF_RELAOD_DAILY);
    }

}
