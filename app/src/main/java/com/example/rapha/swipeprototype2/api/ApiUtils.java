package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.Distribution;
import com.example.rapha.swipeprototype2.categoryDistribution.DistributionContainer;

import java.util.LinkedList;

public class ApiUtils {

    /**
     * Fetches the necessary amount of news articles we want for every category and
     * requests them from the api.
     * @param distributionContainer
     * @return All news articles for the different categories and api calls with the
     * correct distribution in one List.
     * @throws Exception
     */
    public static LinkedList<NewsArticle> buildNewsArticlesList(DistributionContainer distributionContainer)throws Exception{
        // To store all the articles from the different api calls.
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        LinkedList<Distribution> distribution = distributionContainer.getDistributionAsLinkedList();
        for(int i = 0; i < distribution.size(); i++){
            Distribution currentDistribution = distribution.get(i);
            newsArticles.addAll(buildQueryAndFetchArticlesFromApi(currentDistribution));
        }
        return newsArticles;
    }

    /**
     * Creates a query for the corresponding category and requests the amount
     * defined in category.amountToRequestFromApi for it.
     * @param distribution
     * @return The news articles it received from the api with the query.
     * @throws Exception
     */
    private static LinkedList<NewsArticle> buildQueryAndFetchArticlesFromApi(Distribution distribution)throws Exception{
        NewsApi newsApi = new NewsApi();
        NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder();
        queryBuilder.setQueryCategory(distribution.categoryId);
        queryBuilder.setNumberOfNewsArticles(distribution.amountToFetchFromApi);
        return newsApi.queryNewsArticles(queryBuilder);
    }

    public static LinkedList<NewsArticle> orderRandomly(LinkedList<NewsArticle> newsArticles){
            return new LinkedList<NewsArticle>();
    }
}
