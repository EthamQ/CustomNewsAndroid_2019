package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.categoryDistribution.FilterNewsService;

public class NewsCategoryUtils {

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
        int totalAmount = FilterNewsService.MAX_NUMBER_OF_ARTICLES;
        int result = (int)(percentage * totalAmount);
        return result;
    }
}
