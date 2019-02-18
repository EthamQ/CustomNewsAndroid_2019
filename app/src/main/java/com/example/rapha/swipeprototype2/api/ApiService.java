package com.example.rapha.swipeprototype2.api;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.IKeyWordProvider;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.FilterNewsService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.http.HttpRequest;
import com.example.rapha.swipeprototype2.utils.DateUtils;

import java.util.LinkedList;
import java.util.List;

public class ApiService {

    // How old should the news articles be? (days)
    public static int AMOUNT_DAYS_BEFORE_TODAY = 14;
    public static final int MAX_NUMBER_OF_ARTICLES = 30;

    /**
     * Retrieves news articles from the NewsApi and returns them in a list.
     * The number of articles per category are already calculated and the list
     * has the correct distribution of them.
     * The maximum number of returned articles is defined in FilterNewsService.
     *
     * @return
     * @throws Exception
     */
    public static LinkedList<NewsArticle> getAllArticlesNewsApi(SwipeFragment swipeFragment, List<UserPreferenceRoomModel> userPreferenceRoomModels) throws Exception{
        return ApiUtils.buildNewsArticlesList(swipeFragment, FilterNewsService.getCategoryDistribution(userPreferenceRoomModels));
    }

    public static void getArticlesNewsApiByKeyWords(HttpRequest httpRequest, String[] queryWords, int language, int amount, int daysBefore) throws Exception{
        NewsApiQueryBuilder builder = new NewsApiQueryBuilder(language);
        builder.setDateFrom(DateUtils.getDateBefore(daysBefore));
        builder.addQueryWord(queryWords);
        builder.setNumberOfNewsArticles(amount);
        builder.buildQuery();
        NewsApi newsApi = new NewsApi();
        Log.d("oftheday", "Query: " + builder.getQuery());
        newsApi.queryNewsArticlesAsync(httpRequest, builder);
    }
}
