package com.raphael.rapha.myNews.api;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.categoryDistribution.DistributionService;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.util.LinkedList;
import java.util.List;

public class SwipeApiService {

    public static final int AMOUNT_REQUEST_FROM_API = 200;

    /**
     * Retrieves news articles from the NewsApi and returns them in a list.
     * The number of articles per category are already calculated and the list
     * has the correct distribution of them.
     * @return
     * @throws Exception
     */
    public static LinkedList<NewsArticle> getAllArticlesApiForSwipeCards(SwipeFragment swipeFragment, List<NewsCategoryRatingRoomModel> userPreferenceRoomModels) throws Exception{
        return SwipeApiServiceHelper.buildNewsArticlesList(swipeFragment, DistributionService.getCategoryDistribution(userPreferenceRoomModels));
    }
}
