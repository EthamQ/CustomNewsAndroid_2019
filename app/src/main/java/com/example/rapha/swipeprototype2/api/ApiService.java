package com.example.rapha.swipeprototype2.api;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.rapha.swipeprototype2.MainActivity;
import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.UserPreferences.FilterNewsService;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.Finance;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.utils.DateUtils;

import java.util.LinkedList;

public class ApiService {

    /**
     * Retrieves news articles from the NewsApi and returns them in a list.
     * The number of articles per category are already calculated and the list
     * has the correct distribution of them.
     * The maximum number of returned articles is defined in FilterNewsService.
     *
     * @return
     * @throws Exception
     */
    public static void getAllArticlesNewsApi(MainActivity mainActivity)throws Exception{
        Log.d("ää", "getAllArticlesNewsApi");
        FilterNewsService.getCategoryDistribution(mainActivity);

        // ApiUtils.buildNewsArticlesList(FilterNewsService.getCategoryDistribution(mainActivity));
    }

    public static void getAllArticlesNewsApiCallback(CategoryDistribution distribution, MainActivity mainActivity) throws Exception{
        Log.d("ää", "inside getAllArticlesNewsApiCallback");
        LinkedList<NewsArticle> newsArticlesNewsApi;
        newsArticlesNewsApi = ApiUtils.buildNewsArticlesList(distribution);
        mainActivity.loadArticlesCallback(newsArticlesNewsApi);
        mainActivity.test();

    }




    public static int getTotalAmountNewsArticles(LinkedList<NewsArticle> newsArticles){
        return newsArticles.get(0).getTotalAmountInThisQuery();
    }

}
