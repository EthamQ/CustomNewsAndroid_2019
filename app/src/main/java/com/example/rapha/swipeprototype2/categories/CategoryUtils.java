package com.example.rapha.swipeprototype2.categories;

import com.example.rapha.swipeprototype2.UserPreferences.FilterNewsService;

public class CategoryUtils {

    public static int calculateDistribution(NewsCategory newsCategory, int totalRating){
        double percentage = ((double)newsCategory.getRating() / (double)totalRating);
        int totalAmount = FilterNewsService.MAX_NUMBER_OF_ARTICLES;
        int result = (int)(percentage * totalAmount);
        return result;
    }
}
