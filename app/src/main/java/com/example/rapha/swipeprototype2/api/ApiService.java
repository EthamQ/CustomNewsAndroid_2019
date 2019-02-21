package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.categoryDistribution.Distribution;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.FilterNewsService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.http.HttpRequest;
import com.example.rapha.swipeprototype2.utils.DateService;

import java.util.LinkedList;
import java.util.List;

public class ApiService {

    // How old should the news articles be? (days)
    public static int AMOUNT_DAYS_BEFORE_TODAY = 7;
    public static final int MAX_NUMBER_OF_ARTICLES = 100;

    /**
     * Request articles from the api, query for the values given as input parameters.
     * It is void because it will call a callback function when the articles are retrieved.
     * The class that defines this callback function is defined in the HttpRequest object.
     * @param httpRequest
     * @param queryWords
     * @param language
     * @param amount
     * @param daysBefore
     * @throws Exception
     */
    public static void getArticlesNewsApiByKeyWords(HttpRequest httpRequest, String[] queryWords, int language, int amount, int daysBefore) throws Exception{
        NewsApiQueryBuilder builder = new NewsApiQueryBuilder(language);
        builder.setDateFrom(DateService.getDateBefore(daysBefore));
        builder.addQueryWord(queryWords);
        builder.setNumberOfNewsArticles(amount);
        builder.buildQuery();
        NewsApi newsApi = new NewsApi();
        newsApi.queryNewsArticlesAsync(httpRequest, builder);
    }
}
