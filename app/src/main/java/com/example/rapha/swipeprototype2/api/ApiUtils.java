package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.languages.Language;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.roomDatabase.ArticleLanguageLinkDbService;
import com.example.rapha.swipeprototype2.roomDatabase.LanguageCombinationDbService;
import com.example.rapha.swipeprototype2.roomDatabase.OffsetDbService;
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

        // Api call for every category and its distribution.
        for(int i = 0; i < distribution.size(); i++){
            Distribution currentDistribution = distribution.get(i);
            // One Api call for every selected language
            currentDistribution.balanceWithLanguageDistribution(languages.size());
            for(int j = 0; j < languages.size(); j++){
                newsArticles.addAll(buildQueryAndFetchArticlesFromApi(swipeFragment, currentDistribution, languages.get(j)));
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
    private static LinkedList<NewsArticle> buildQueryAndFetchArticlesFromApi(SwipeFragment swipeFragment, Distribution distribution, Language language)throws Exception{
        NewsApi newsApi = new NewsApi();
        NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder(language.languageId);
        queryBuilder.setQueryCategory(distribution.categoryId, swipeFragment);
        queryBuilder.setNumberOfNewsArticles(distribution.amountToFetchFromApi);
        queryBuilder.setDateFrom(DateUtils.getDateBefore(ApiService.AMOUNT_DAYS_BEFORE_TODAY));
        LinkedList<NewsArticle> fetchedArticles = newsApi.queryNewsArticles(queryBuilder);
        storeDataAboutFetchedArticles(swipeFragment, fetchedArticles, distribution);
        return fetchedArticles;
    }

    private static void storeDataAboutFetchedArticles(SwipeFragment swipeFragment, LinkedList<NewsArticle> fetchedArticles, Distribution distribution){
        LanguageCombinationDbService languageCombinationDbService = LanguageCombinationDbService.getInstance(swipeFragment.getActivity().getApplication());
        OffsetDbService offsetDbService = OffsetDbService.getInstance(swipeFragment.getActivity().getApplication());
        ArticleLanguageLinkDbService articleLanguageLinkDbService = ArticleLanguageLinkDbService.getInstance(swipeFragment.getActivity().getApplication());
        int combinationId = languageCombinationDbService.insertLanguageCombination(LanguageSettingsService.loadChecked(swipeFragment.mainActivity));
        int languageCombinationId = offsetDbService.saveRequestOffset(
                distribution.amountToFetchFromApi,
                distribution.categoryId,
                combinationId
        );
        articleLanguageLinkDbService.linkArticleWithLanguage(fetchedArticles, languageCombinationId);
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
