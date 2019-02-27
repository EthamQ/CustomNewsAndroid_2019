package com.raphael.rapha.myNews.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.api.apiQuery.NewsApiQueryBuilder;
import com.raphael.rapha.myNews.dateOffset.DateOffsetService;
import com.raphael.rapha.myNews.loading.LoadingService;
import com.raphael.rapha.myNews.temporaryDataStorage.DateOffsetDataStorage;
import com.raphael.rapha.myNews.languages.Language;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.roomDatabase.LanguageCombinationDbService;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.categoryDistribution.Distribution;
import com.raphael.rapha.myNews.categoryDistribution.DistributionContainer;
import com.raphael.rapha.myNews.utils.DateService;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

import java.util.LinkedList;

public class SwipeApiServiceHelper {

    /**
     * Uses the previously calculated distribution to request the correct amount for every category
     * from the api.
     * @param distributionContainer
     * @return All news articles for the different categories and api calls with the
     * correct distribution in one List.
     * @throws Exception
     */
    public static LinkedList<NewsArticle> buildNewsArticlesList(SwipeFragment swipeFragment, DistributionContainer distributionContainer)throws Exception{
        // To store all the articles from the different api calls.
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        LinkedList<Distribution> distribution = distributionContainer.getDistributionAsLinkedList();
        boolean[] languageSettings = LanguageSettingsService.loadChecked(swipeFragment.mainActivity);
        LinkedList<Language> languages = new LinkedList<>();
        for(int languageIndex = LanguageSettingsService.INDEX_ENGLISH; languageIndex < languageSettings.length; languageIndex++){
            if(languageSettings[languageIndex]){
                languages.add(new Language(languageIndex));
            }
        }

        // To give the user feedback how much he still has to wait
        int numberRequests = distribution.size() * languages.size();
        LoadingService.setNumberOfSentRequests(numberRequests);
        swipeFragment.updateLoadingText(LoadingService.getNumberOfAnswersReceivedText());

        // Api call for every category and its distribution.
        for(int i = 0; i < distribution.size(); i++){
            Distribution currentDistribution = distribution.get(i);
            // One Api call for every selected language
            currentDistribution.balanceWithLanguageDistribution(languages.size());
            for(int j = 0; j < languages.size(); j++){
                newsArticles.addAll(buildQueryAndFetchArticlesFromApi(swipeFragment, currentDistribution, languages.get(j)));
            }
        }

        // Sort articles by date
        Collections.sort(newsArticles, (a, b) -> {
            DateTime date1 = new DateTime( a.publishedAt );
            DateTime date2 = new DateTime( b.publishedAt );
            if(date1.isBefore(date2)){
                return 1;
            }
            else if(date1.isAfter(date2)){
                return -1;
            }
            else return 0;
        });

        return newsArticles;
    }

    /**
     * Initializes a query builder to build a query string for the api request
     * and passes it to another class to send the query.
     * Uses the topics from the database, the news category id, the language id
     * and date values stored elsewhere  as information.
     * @param distribution
     * @return The news articles it received from the api with the query.
     * @throws Exception
     */
    private static LinkedList<NewsArticle> buildQueryAndFetchArticlesFromApi(SwipeFragment swipeFragment, Distribution distribution, Language language)throws Exception{
        NewsApi newsApi = new NewsApi();
        NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder(language.languageId);
        queryBuilder.setQueryListener(swipeFragment);
        queryBuilder.setHttpRequester(swipeFragment);
        queryBuilder.setQueryCategory(distribution.categoryId, swipeFragment.liveKeyWords);
        queryBuilder.setNumberOfNewsArticles(distribution.amountToFetchFromApi);
        String dateFrom = DateService.getDateBefore(ApiService.AMOUNT_DAYS_BEFORE_TODAY);
        queryBuilder.setDateFrom(dateFrom);
        String dateTo = DateOffsetDataStorage.getOffsetForCategory(distribution.categoryId);
        if(!dateTo.isEmpty()){
            if(DateService.datesEqual(dateFrom, dateTo)){
                DateOffsetService.resetDateOffsetForCurrentLanguages(swipeFragment, distribution.categoryId);
            }
            else{
                queryBuilder.setDateTo(dateTo);
            }
        }
        LinkedList<NewsArticle> fetchedArticles = newsApi.queryNewsArticles(queryBuilder);
        return fetchedArticles;
    }

}
