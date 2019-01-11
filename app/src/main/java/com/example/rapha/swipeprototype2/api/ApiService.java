package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.UserPreferences.FilterNewsService;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.Finance;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.utils.DateUtils;

import java.util.LinkedList;

public class ApiService {



    public static LinkedList<NewsArticle> getAllArticlesNewsApi() throws Exception{
        return ApiUtils.buildNewsArticlesList(FilterNewsService.getCategoryDistribution());
    }


    public static int getTotalAmountNewsArticles(LinkedList<NewsArticle> newsArticles){
        return newsArticles.get(0).getTotalAmountInThisQuery();
    }

}
