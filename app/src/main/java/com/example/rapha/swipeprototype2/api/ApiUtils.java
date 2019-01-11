package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.NewsCategory;

import java.util.LinkedList;

public class ApiUtils {

    public static LinkedList<NewsArticle> buildNewsArticlesList(CategoryDistribution categoryDistribution)throws Exception{
        NewsApi newsApi = new NewsApi();
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        LinkedList<NewsCategory> newsCategories = categoryDistribution.getDistribution();
        for(int i = 0; i < newsCategories.size(); i++){
            NewsCategory currentCategory = newsCategories.get(i);
            NewsApiQueryBuilder queryBuilder = new NewsApiQueryBuilder();
            queryBuilder.setQueryCategory(currentCategory.getCategoryID());
            LinkedList<NewsArticle> articlesForCurrentCategory = newsApi.queryNewsArticles(queryBuilder);
            newsArticles.addAll(articlesForCurrentCategory);
        }
        return newsArticles;
    }
}
