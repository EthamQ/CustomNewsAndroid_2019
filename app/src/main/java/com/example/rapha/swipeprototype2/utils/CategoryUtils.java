package com.example.rapha.swipeprototype2.utils;

import com.example.rapha.swipeprototype2.newsCategories.Finance;
import com.example.rapha.swipeprototype2.newsCategories.Food;
import com.example.rapha.swipeprototype2.newsCategories.Movie;
import com.example.rapha.swipeprototype2.newsCategories.Politics;
import com.example.rapha.swipeprototype2.newsCategories.Technology;

public class CategoryUtils {

    public static String[] getQueryWords(int category, String language){
        switch(category){
            case Politics.CATEGORY_ID: return Politics.POLITICS_QUERY_STRINGS_EN;
            case Finance.CATEGORY_ID: return Finance.FINANCE_QUERY_STRINGS_EN;
            case Movie.CATEGORY_ID: return Movie.MOVIE_QUERY_STRINGS_EN;
            case Food.CATEGORY_ID: return Food.FOOD_QUERY_STRINGS_EN;
            case Technology.CATEGORY_ID: return Technology.TECHNOLOGY_QUERY_STRINGS_EN;
            default: return new String[]{"a"};
        }
    }
}
