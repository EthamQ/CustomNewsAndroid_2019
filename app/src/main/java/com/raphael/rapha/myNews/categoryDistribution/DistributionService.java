package com.raphael.rapha.myNews.categoryDistribution;


import com.raphael.rapha.myNews.api.ApiService;
import com.raphael.rapha.myNews.api.SwipeApiService;
import com.raphael.rapha.myNews.newsCategories.NewsCategory;
import com.raphael.rapha.myNews.newsCategories.NewsCategoryService;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRoomModel;

import java.util.LinkedList;
import java.util.List;

public class DistributionService {

    /**
     * Calculates the amount for every news category that should be requested from the api.
     * @param userPreferenceRoomModels
     * @return A DistributionContainer containing the distribution for every single news category.
     */
    public static DistributionContainer getCategoryDistribution(List<NewsCategoryRatingRoomModel> userPreferenceRoomModels){
        LinkedList<Distribution> distribution =
                NewsCategoryService.retrieveAndSetCategoryRating(userPreferenceRoomModels).getCategoryDistribution();
        DistributionContainer distributionContainer = new DistributionContainer();
        distributionContainer.setDistribution(distribution);
        return distributionContainer;
    }

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
