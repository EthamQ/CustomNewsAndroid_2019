package com.example.rapha.swipeprototype2.api;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.languages.Language;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.ArticleLanguageLinkDbService;
import com.example.rapha.swipeprototype2.roomDatabase.LanguageCombinationDbService;
import com.example.rapha.swipeprototype2.roomDatabase.OffsetDbService;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.IInsertsLanguageCombination;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.Distribution;
import com.example.rapha.swipeprototype2.categoryDistribution.DistributionContainer;
import com.example.rapha.swipeprototype2.utils.DateUtils;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import java.util.LinkedList;

public class ApiUtils {

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
                Log.d("newswipe", "request with language index: " + new Language(languageIndex).languageId);
                languages.add(new Language(languageIndex));
            }
        }

        // Api call for every category and its distribution.
        for(int i = 0; i < distribution.size(); i++){
            Distribution currentDistribution = distribution.get(i);
            // One Api call for every selected language
            currentDistribution.balanceWithLanguageDistribution(languages.size());
            for(int j = 0; j < languages.size(); j++){
                newsArticles.addAll(buildQueryAndFetchArticlesFromApi(swipeFragment, currentDistribution, languages.get(j)));
            }
        }

        Collections.sort(newsArticles, new Comparator<NewsArticle>() {
            @Override
            public int compare(NewsArticle a, NewsArticle b) {
                DateTime date1 = new DateTime( a.publishedAt );
                DateTime date2 = new DateTime( b.publishedAt );
                if(date1.isBefore(date2)){
                    return 1;
                }
                else if(date1.isAfter(date2)){
                    return -1;
                }
                else return 0;
            }
        });

        return newsArticles;
    }

    /**
     * Creates a query string for every category and requests the amount
     * defined in "distribution" for every category from the api.
     * @param distribution
     * @return The news articles it received from the api with the query.
     * @throws Exception
     */
    private static LinkedList<NewsArticle> buildQueryAndFetchArticlesFromApi(SwipeFragment swipeFragment, Distribution distribution, Language language)throws Exception{
        NewsApi newsApi = new NewsApi();
        NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder(language.languageId);
        queryBuilder.setQueryCategory(distribution.categoryId, swipeFragment);
        queryBuilder.setNumberOfNewsArticles(distribution.amountToFetchFromApi);
        queryBuilder.setDateFrom(DateUtils.getDateBefore(ApiService.AMOUNT_DAYS_BEFORE_TODAY));
        String requestOffset = "";
        if(distribution.categoryId == NewsCategoryContainer.Politics.CATEGORY_ID){
            requestOffset = swipeFragment.requestOffsetPolitics;
        }
        if(distribution.categoryId == NewsCategoryContainer.Movie.CATEGORY_ID){
            requestOffset = swipeFragment.requestOffsetMovie;
        }
        if(distribution.categoryId == NewsCategoryContainer.Finance.CATEGORY_ID){
            requestOffset = swipeFragment.requestOffsetFinance;
        }
        if(distribution.categoryId == NewsCategoryContainer.Food.CATEGORY_ID){
            requestOffset = swipeFragment.requestOffsetFood;
        }
        if(distribution.categoryId == NewsCategoryContainer.Technology.CATEGORY_ID){
            requestOffset = swipeFragment.requestOffsetTechnology;
        }

        if(!requestOffset.isEmpty()){
            Log.d("newswipe", "category: " + distribution.categoryId + ", offset in query: " + requestOffset);
            queryBuilder.setDateTo(requestOffset);
            checkDayEqual(DateUtils.getDateBefore(ApiService.AMOUNT_DAYS_BEFORE_TODAY), requestOffset, distribution.categoryId);
        }
        else{
            Log.d("newswipe", "no offset set");
        }
        LinkedList<NewsArticle> fetchedArticles = newsApi.queryNewsArticles(queryBuilder);
        return fetchedArticles;
    }

    private static boolean checkDayEqual(String dateFrom, String dateTo, int categoryId){
        DateTime from = new DateTime( dateFrom );
        DateTime to = new DateTime( dateTo );
        int dayFrom = from.getDayOfMonth();
        int monthFrom = from.getMonthOfYear();
        int dayTo = to.getDayOfMonth();
        int monthTo = to.getMonthOfYear();
        if(dayFrom == dayTo && monthFrom == monthTo){
            Log.d("newswipe", "#### NO NEWS ARTICLES, ");
            return true;
        }
        return false;
    }


    /**
     * Return the LinkedList that the function receives in random order.
     * @param newsArticles
     * @return
     */
    public static LinkedList<NewsArticle> orderRandomly(LinkedList<NewsArticle> newsArticles){
        LinkedList<NewsArticle> randomOrderedList = new LinkedList<>();
        while(newsArticles.size() > 0){
            int randomIndex = ThreadLocalRandom.current().nextInt(0, newsArticles.size());
            randomOrderedList.add(newsArticles.get(randomIndex));
            newsArticles.remove(randomIndex);
        }
        return randomOrderedList;
    }

    /**
     * Return the LinkedList that the function receives in random order.
     * @param newsArticles
     * @return
     */
    public static LinkedList<NewsArticle> orderByDate(LinkedList<NewsArticle> newsArticles){
        LinkedList<NewsArticle> randomOrderedList = new LinkedList<>();
        while(newsArticles.size() > 0){
            int randomIndex = ThreadLocalRandom.current().nextInt(0, newsArticles.size());
            randomOrderedList.add(newsArticles.get(randomIndex));
            newsArticles.remove(randomIndex);
        }
        return randomOrderedList;
    }

}
