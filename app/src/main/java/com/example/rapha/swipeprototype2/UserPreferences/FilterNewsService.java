package com.example.rapha.swipeprototype2.UserPreferences;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.categories.Categories;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

public class FilterNewsService {

    public static final int MAX_NUMBER_OF_ARTICLES = 100;

    public static CategoryDistribution getCategoryDistribution(List<UserPreferenceRoomModel> userPreferenceRoomModels){
        UserPreference userPreference = FilterNewsUtils.retrieveAndSetCategoryRating(new UserPreference(), userPreferenceRoomModels);
        LinkedList<NewsCategory> distribution = userPreference.getCategories().getCategoryDistribution();
        CategoryDistribution categoryDistribution = new CategoryDistribution();
        categoryDistribution.setDistribution(distribution);
        return categoryDistribution;
    }

}
