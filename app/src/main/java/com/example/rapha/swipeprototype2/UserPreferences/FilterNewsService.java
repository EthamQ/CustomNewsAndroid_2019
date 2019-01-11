package com.example.rapha.swipeprototype2.UserPreferences;

import android.util.Log;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.categories.Categories;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.NewsCategory;

import java.util.LinkedList;

public class FilterNewsService {

    public static final int NUMBER_OF_ARTICLES = 100;

    public static CategoryDistribution getCategoryDistribution(){
        UserPreference userPreference = new UserPreference();
        userPreference.getCategories().finance.setRating(20);
        userPreference.getCategories().politics.setRating(10);
        userPreference.getCategories().technology.setRating(10);
        userPreference.getCategories().movie.setRating(10);
        userPreference.getCategories().food.setRating(10);
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
