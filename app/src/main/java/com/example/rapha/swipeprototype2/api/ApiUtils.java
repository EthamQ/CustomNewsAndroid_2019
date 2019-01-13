package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryDistribution;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;

import java.util.LinkedList;

public class ApiUtils {

    /**
     * Fetches the necessary amount of news articles we want for every category and
     * requests them from the api.
     * @param newsCategoryDistribution
     * @return All news articles for the different categories and api calls with the
     * correct distribution in one List.
     * @throws Exception
     */
    public static LinkedList<NewsArticle> buildNewsArticlesList(NewsCategoryDistribution newsCategoryDistribution)throws Exception{
        // To store all the articles from the different api calls.
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        // Contains the distribution of ratings for the different categories.
        LinkedList<NewsCategory> distribution = newsCategoryDistribution.getDistribution();
        for(int i = 0; i < distribution.size(); i++){
            // currentCategory.amountToRequestFromApi contains how many articles
            // we should request from the api.
            NewsCategory currentCategory = distribution.get(i);
            newsArticles.addAll(buildQueryAndFetchArticlesFromApi(currentCategory));
        }
        return newsArticles;
    }

    /**
     * Creates a query for the corresponding category and requests the amount
     * defined in category.amountToRequestFromApi for it.
     * @param category
     * @return The news articles it received from the api with the query.
     * @throws Exception
     */
    private static LinkedList<NewsArticle> buildQueryAndFetchArticlesFromApi(NewsCategory category)throws Exception{
        NewsApi newsApi = new NewsApi();
        NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder();
        queryBuilder.setQueryCategory(category.getCategoryID());
        queryBuilder.setNumberOfNewsArticles(category.amountToRequestFromApi);
        return newsApi.queryNewsArticles(queryBuilder);
    }

    public static LinkedList<NewsArticle> orderRandomly(LinkedList<NewsArticle> newsArticles){
            return new LinkedList<NewsArticle>();
    }
}
