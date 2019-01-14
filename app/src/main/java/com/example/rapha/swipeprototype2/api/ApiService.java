package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.FilterNewsService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

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
    public static LinkedList<NewsArticle> getAllArticlesNewsApi(List<UserPreferenceRoomModel> userPreferenceRoomModels) throws Exception{
        return ApiUtils.buildNewsArticlesList(FilterNewsService.getCategoryDistribution(userPreferenceRoomModels));
    }


    public static int getTotalAmountNewsArticles(LinkedList<NewsArticle> newsArticles){
        return newsArticles.get(0).getTotalAmountInThisQuery();
    }

}
