package com.example.rapha.swipeprototype2.UserPreferences;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.categories.Categories;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.NewsCategory;

import java.util.LinkedList;

public class FilterNewsService {

    public static final int MAX_NUMBER_OF_ARTICLES = 100;

    public static CategoryDistribution getCategoryDistribution(Application application, LifecycleOwner lifecycleOwner){
        UserPreference userPreference = FilterNewsUtils.retrieveAndSetCategoryRating(new UserPreference(), application, lifecycleOwner);
        LinkedList<NewsCategory> distribution = userPreference.getCategories().getCategoryDistribution();
        CategoryDistribution categoryDistribution = new CategoryDistribution();
        categoryDistribution.setDistribution(distribution);
        for(int i = 0; i < distribution.size(); i++){
            Log.d("**", "amount in query: " + distribution.get(i).amountInCurrentQuery);
            Log.d("**", "category id: " + distribution.get(i).getCategoryID());
        }
        return categoryDistribution;
    }

}
