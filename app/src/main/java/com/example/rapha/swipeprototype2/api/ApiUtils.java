package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.NewsCategory;

import java.util.LinkedList;

public class ApiUtils {

    /**
     * Builds
     * @param categoryDistribution
     * @return
     * @throws Exception
     */
    public static LinkedList<NewsArticle> buildNewsArticlesList(CategoryDistribution categoryDistribution)throws Exception{
        NewsApi newsApi = new NewsApi();
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        LinkedList<NewsCategory> distribution = categoryDistribution.getDistribution();
        for(int i = 0; i < distribution.size(); i++){
            NewsCategory currentCategory = distribution.get(i);
            NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder();
            queryBuilder.setQueryCategory(currentCategory.getCategoryID());
            queryBuilder.setNumberOfNewsArticles(currentCategory.amountInCurrentQuery);
            LinkedList<NewsArticle> articlesForCurrentCategory = newsApi.queryNewsArticles(queryBuilder);
            newsArticles.addAll(articlesForCurrentCategory);
        }
        return newsArticles;
    }
}
