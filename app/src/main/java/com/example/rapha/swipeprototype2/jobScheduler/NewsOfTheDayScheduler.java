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
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.time.ApiRequestTimeService;

import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NewsOfTheDayScheduler extends JobService implements IHttpRequester {

    String TAG = "scheduler";
    NewsArticleDbService newsArticleDbService;
    KeyWordDbService keyWordDbService;
    boolean notificationWasSent = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "job started");
        newsArticleDbService = NewsArticleDbService.getInstance(getApplication());
        keyWordDbService = KeyWordDbService.getInstance(getApplication());
        // To not show them to the user again.
        setReadArticlesToArchived();
        // Request articles for liked keywords.
        Observer observer = getObserverToRequestArticles(jobParameters);
        keyWordDbService.getAllLikedKeyWords().observeForever(observer);
        return true;
    }

    private void setReadArticlesToArchived(){
        newsArticleDbService.getAllReadDailyArticles().observeForever(new Observer<List<NewsArticleRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<NewsArticleRoomModel> readArticles) {
                if(readArticles.size() > 0){
                    for(int i = 0; i < readArticles.size(); i++){
                        newsArticleDbService.setAsArchived(readArticles.get(i));
                    }
                }
            }
        });
    }

    int numberOfRequests = 0;
    private Observer getObserverToRequestArticles(JobParameters jobParameters){
        this.jobParameters = jobParameters;
        Observer<List<KeyWordRoomModel>> observer = new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topicsToLookFor) {
                if(topicsToLookFor.size() >= NewsOfTheDayFragment.ARTICLE_MINIMUM){
                    Log.d(TAG, "in onchanged");
                    numberOfRequests = topicsToLookFor.size();
                    for(int i = 0; i < topicsToLookFor.size(); i++) {
                        HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
                        httpRequestInfo.setDataOfRequester(topicsToLookFor.get(i).keyWord);
                        HttpRequest httpRequest = new HttpRequest(NewsOfTheDayScheduler.this, httpRequestInfo);
                        keyWordDbService.setAsNewsOfTheDayKeyWord(topicsToLookFor.get(i));
                        String[] keyWords = new QueryWordTransformation().getKeyWordsFromTopics(topicsToLookFor.get(i));
                        try {
                            ApiService.getArticlesNewsApiByKeyWords(
                                    httpRequest, keyWords, LanguageSettingsService.INDEX_ENGLISH
                            );
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

    int numberResults = 0;
    JobParameters jobParameters;
    @Override
    public void httpResultCallback(HttpRequestInfo info) {
        numberResults++;
        Log.d(TAG, "reached httpResultCallback");
        LinkedList<NewsArticle> articlesForKeyword = new LinkedList<>();
        JSONObject newsArticleJson = (JSONObject) info.getRequestResponse();
        try {
            articlesForKeyword = NewsApiUtils.jsonToNewsArticleArray(newsArticleJson, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(articlesForKeyword.size() > 0){
            if(!notificationWasSent){
                // NewsOfTheDayNotificationService.sendNotificationLoadedDailyNews(this);
                notificationWasSent = true;
            }
            setDateArticlesLoaded();
            for(int i = 0; i < articlesForKeyword.size(); i++) {
                Log.d(TAG, "adds this article to db: " + articlesForKeyword.get(i));

                NewsArticle articleToAdd = articlesForKeyword.get(i);
                articleToAdd.foundWithKeyWord = (String) info.getDataOfRequester();
                newsArticleDbService.insert(articleToAdd);
            }
        }
        if(numberResults == numberOfRequests){
            jobFinished(jobParameters, false);
            NewsOfTheDayNotificationService.sendNotificationLoadedDailyNews(this);
        }
    }

    private void setDateArticlesLoaded(){
        ApiRequestTimeService.saveLastLoadedDefault(
                getApplicationContext(),
                new Date(),
                ApiRequestTimeService.TIME_OF_RELAOD_DAILY);
    }

}
