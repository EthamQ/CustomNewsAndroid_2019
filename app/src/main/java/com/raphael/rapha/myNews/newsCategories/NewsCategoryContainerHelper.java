package com.raphael.rapha.myNews.newsCategories;

import com.raphael.rapha.myNews.api.ApiService;
import com.raphael.rapha.myNews.api.SwipeApiService;

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

