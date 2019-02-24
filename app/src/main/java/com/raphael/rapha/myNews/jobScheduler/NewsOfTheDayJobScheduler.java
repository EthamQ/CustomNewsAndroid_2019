package com.raphael.rapha.myNews.jobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.NewsOfTheDayFragment;
import com.raphael.rapha.myNews.api.NewsApiHelper;
import com.raphael.rapha.myNews.api.NewsOfTheDayApiService;
import com.raphael.rapha.myNews.http.HttpRequest;
import com.raphael.rapha.myNews.http.HttpRequestInfo;
import com.raphael.rapha.myNews.http.IHttpRequester;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.loading.DailyNewsLoadingService;
import com.raphael.rapha.myNews.topics.TopicWordsTransformation;
import com.raphael.rapha.myNews.notifications.NewsOfTheDayNotificationService;
import com.raphael.rapha.myNews.roomDatabase.KeyWordDbService;
import com.raphael.rapha.myNews.roomDatabase.NewsArticleDbService;
import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.sharedPreferencesAccess.NewsOfTheDayTimeService;

import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NewsOfTheDayJobScheduler extends JobService implements IHttpRequester {

    String TAG = "scheduler";
    NewsArticleDbService newsArticleDbService;
    KeyWordDbService keyWordDbService;
    JobParameters jobParameters;
    // Set them and compare them later to know when all responses have arrived.
    int numberOfSentRequests = 0;
    int numberOfReceivedResponses = 0;

    LiveData<List<KeyWordRoomModel>> likedTopicsLiveData;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        DailyNewsLoadingService.reactOnLoadArticlesUnsuccessful(NewsOfTheDayJobScheduler.this);
        Log.d(TAG, "job started");
        newsArticleDbService = NewsArticleDbService.getInstance(getApplication());
        keyWordDbService = KeyWordDbService.getInstance(getApplication());
        // To not show them to the user again.
        setReadArticlesToArchived();
        // Request articles for liked keywords for the new request.
        Observer observer = getObserverToRequestArticles(jobParameters);
        likedTopicsLiveData = keyWordDbService.getAllLikedKeyWords();
        likedTopicsLiveData.observeForever(observer);
        jobFinished(jobParameters, false);
        return true;
    }

    private void setReadArticlesToArchived(){
        List<NewsArticleRoomModel> readArticles = new LinkedList<>();
        LiveData<List<NewsArticleRoomModel>> newsOfTheDayLiveData = newsArticleDbService.getAllNewsOfTheDayArticles();
        newsOfTheDayLiveData.observeForever(new Observer<List<NewsArticleRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<NewsArticleRoomModel> newsOfTheDayArticles) {
                if(NewsOfTheDayTimeService.firstTimeLoadingData(getApplicationContext())){
                    newsOfTheDayLiveData.removeObserver(this);
                }

                for(int j = 0; j < newsOfTheDayArticles.size(); j++){
                    if(!newsOfTheDayArticles.get(j).archived && newsOfTheDayArticles.get(j).hasBeenRead){
                        readArticles.add(newsOfTheDayArticles.get(j));
                    }
                }

                if(!readArticles.isEmpty()){
                    for(int i = 0; i < readArticles.size(); i++){
                        newsArticleDbService.setAsArchived(readArticles.get(i));
                        Log.d("archived", "Set to archived: " + "archived: " + readArticles.get(i).archived + ", read: " + readArticles.get(i).hasBeenRead +", title: " +  readArticles.get(i).title);
                    }
                    newsOfTheDayLiveData.removeObserver(this);
                }
            }
        });
    }

    private Observer getObserverToRequestArticles(JobParameters jobParameters){
        this.jobParameters = jobParameters;
        Observer<List<KeyWordRoomModel>> observer = new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topicsToLookFor) {
                if(topicsToLookFor.size() >= NewsOfTheDayFragment.ARTICLE_MINIMUM){
                    numberOfSentRequests = topicsToLookFor.size();
                    // Provides a language id for every topic at the corresponding index.
                    int[] languageIds = LanguageSettingsService.generateLanguageDistributionNewsOfTheDay(
                            topicsToLookFor.size(),
                            LanguageSettingsService.loadChecked(getApplicationContext())
                    );
                    // Store the current selected languages to still have them when
                    // we load the articles from the database later
                    LanguageSettingsService.saveCheckedLoadedNewsOfTheDay(getApplicationContext());
                    int languageArrayIndex = 0;
                    for(int i = 0; i < topicsToLookFor.size(); i++) {
                        HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
                        // So we can add the keyword it was fond with to the articles later.
                        httpRequestInfo.setDataOfRequester(topicsToLookFor.get(i).keyWord);
                        httpRequestInfo.setInformationCode(languageIds[languageArrayIndex]);
                        httpRequestInfo.setContext(getApplicationContext());
                        HttpRequest httpRequest = new HttpRequest(NewsOfTheDayJobScheduler.this, httpRequestInfo);
                        // So when we load the articles we know which keywords / topics to look for
                        keyWordDbService.setAsNewsOfTheDayKeyWord(topicsToLookFor.get(i));
                        String[] keyWords = new TopicWordsTransformation().getKeyWordsFromTopics(topicsToLookFor.get(i));
                        try {
                            DailyNewsLoadingService.setLoading(true);
                            NewsOfTheDayApiService.getArticlesNewsApiByKeyWords(
                                    httpRequest, keyWords, languageIds[languageArrayIndex++]
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                            DailyNewsLoadingService.setLoading(false);
                        }
                    }
                    likedTopicsLiveData.removeObserver(this);
                }
            }
        };
        return observer;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        DailyNewsLoadingService.loadingInterrupted();
        Log.d(TAG, "job cancelled");
        return true;
    }

    @Override
    public void httpResultCallback(HttpRequestInfo info) {
        numberOfReceivedResponses++;
        Log.d(TAG, "reached httpResultCallback");
        LinkedList<NewsArticle> articlesForKeyword = new LinkedList<>();
        JSONObject newsArticleJson = (JSONObject) info.getRequestResponse();
        try {
            articlesForKeyword = NewsApiHelper.jsonToNewsArticleArray(newsArticleJson, -1, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(articlesForKeyword.size() > 0){
            storeDateLastLoadedData();
            String foundWithKeyWord = (String) info.getDataOfRequester();
            storeArticlesInDatabase(articlesForKeyword, foundWithKeyWord, info.getInformationCode());
        }

        // Send notification when all responses have arrived
        if(numberOfReceivedResponses == numberOfSentRequests){
            DailyNewsLoadingService.setLoading(false);
                NewsOfTheDayNotificationService.sendNotificationLoadedDailyNews(this);
        }
    }

    private void storeDateLastLoadedData(){
        NewsOfTheDayTimeService.saveDateLastLoadedArticles(
                getApplicationContext(),
                new Date()
        );
    }

    private void storeArticlesInDatabase(LinkedList<NewsArticle> newsArticle, String foundWithKeyWord, int languageId){
        for(int i = 0; i < newsArticle.size(); i++) {
            Log.d(TAG, "adds this article to db: " + newsArticle.get(i));
            NewsArticle articleToAdd = newsArticle.get(i);
            // We have set this value before so we know it's stored there.
            articleToAdd.foundWithKeyWord = foundWithKeyWord;
            articleToAdd.languageId = LanguageSettingsService.getLanguageIdAsString(languageId);
            Log.d(TAG, "adds keyword: " + articleToAdd.foundWithKeyWord + ", adds language: " + articleToAdd.languageId);
            newsArticleDbService.insert(articleToAdd);
        }
    }




}
