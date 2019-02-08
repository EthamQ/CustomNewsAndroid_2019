package com.example.rapha.swipeprototype2.api;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.FilterNewsService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

public class ApiService {

    // How old should the news articles be? (days)
    public static int AMOUNT_DAYS_BEFORE_TODAY = 7;
    public static final int MAX_NUMBER_OF_ARTICLES = 100;

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
}
