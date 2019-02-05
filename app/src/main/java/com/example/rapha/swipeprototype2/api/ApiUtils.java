package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.languageSettings.Language;
import com.example.rapha.swipeprototype2.languageSettings.LanguageSettingsService;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.Distribution;
import com.example.rapha.swipeprototype2.categoryDistribution.DistributionContainer;
import com.example.rapha.swipeprototype2.utils.DateUtils;

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
    public static LinkedList<NewsArticle> buildNewsArticlesList(MainActivity mainActivity, DistributionContainer distributionContainer)throws Exception{
        // To store all the articles from the different api calls.
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        LinkedList<Distribution> distribution = distributionContainer.getDistributionAsLinkedList();

        boolean[] languageSettings = LanguageSettingsService.loadChecked(mainActivity);
        LinkedList<Language> languages = new LinkedList<>();
        for(int languageIndex = LanguageSettingsService.INDEX_ENGLISH; languageIndex < languageSettings.length; languageIndex++){
            if(languageSettings[languageIndex]){
                languages.add(new Language(languageIndex));
            }
        }

        // Api call for every category and its distribution.
        for(int i = 0; i < distribution.size(); i++){
            Distribution currentDistribution = distribution.get(i);
            // One Api call for every selected language
            currentDistribution.balanceWithLanguageDistribution(languages.size());
            for(int j = 0; j < languages.size(); j++){
                newsArticles.addAll(buildQueryAndFetchArticlesFromApi(currentDistribution, languages.get(j)));
            }
        }
        return orderRandomly(newsArticles);
    }

    /**
     * Creates a query string for every category and requests the amount
     * defined in "distribution" for every category from the api.
     * @param distribution
     * @return The news articles it received from the api with the query.
     * @throws Exception
     */
    private static LinkedList<NewsArticle> buildQueryAndFetchArticlesFromApi(Distribution distribution, Language language)throws Exception{
        NewsApi newsApi = new NewsApi();
        NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder(language.languageId);
        queryBuilder.setQueryCategory(distribution.categoryId);
        queryBuilder.setNumberOfNewsArticles(distribution.amountToFetchFromApi);
        queryBuilder.setDateFrom(DateUtils.getDateBefore(ApiService.AMOUNT_DAYS_BEFORE_TODAY));
        return newsApi.queryNewsArticles(queryBuilder);
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
}
