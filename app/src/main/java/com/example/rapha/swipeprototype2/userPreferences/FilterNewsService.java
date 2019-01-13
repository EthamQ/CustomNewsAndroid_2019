package com.example.rapha.swipeprototype2.userPreferences;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryDistribution;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

public class FilterNewsService {

    public static final int MAX_NUMBER_OF_ARTICLES = 100;

    public static NewsCategoryDistribution getCategoryDistribution(List<UserPreferenceRoomModel> userPreferenceRoomModels){
        UserPreference userPreference = FilterNewsUtils.retrieveAndSetCategoryRating(new UserPreference(), userPreferenceRoomModels);
        LinkedList<NewsCategory> distribution = userPreference.getCategories().getCategoryDistribution();
        NewsCategoryDistribution newsCategoryDistribution = new NewsCategoryDistribution();
        newsCategoryDistribution.setDistribution(distribution);
        return newsCategoryDistribution;
    }

}
