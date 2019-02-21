package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.ApiService;
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
        double percentage = ((double)newsCategory.getRating() / (double)totalRating);
        int totalAmount = ApiService.MAX_NUMBER_OF_ARTICLES;
        int result = (int)(percentage * totalAmount);
        return result;
    }

}

