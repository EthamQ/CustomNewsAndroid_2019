package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.api.SwipeApiService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.LinkedList;

public class NewsCategoryContainerHelper {

    /**
     * Calculates how many news articles for the news category should be requested
     * by using its rating, the total rating for all categories and the total number
     * of requested articles.
     * @param newsCategory
     * @param totalRating
     * @return
     */
    public static int calculateDistribution(NewsCategory newsCategory, int totalRating){
        int categoryRating = newsCategory.getRating() < 10 ? 10 : newsCategory.getRating();
        double percentage = ((double)categoryRating / (double)totalRating);
        int totalAmount = SwipeApiService.AMOUNT_REQUEST_FROM_API;
        int result = (int)(percentage * totalAmount);
        boolean resultTooBig = result > ApiService.MAX_NUMBER_OF_ARTICLES;
        result = resultTooBig ? ApiService.MAX_NUMBER_OF_ARTICLES : result;
        return result;
    }

}

