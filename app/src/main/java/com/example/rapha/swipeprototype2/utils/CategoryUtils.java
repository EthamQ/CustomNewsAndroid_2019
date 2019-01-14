package com.example.rapha.swipeprototype2.utils;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;

public class CategoryUtils {

    public static String[] getQueryWords(int category, String language){
        switch(category){
            case NewsCategoryContainer.Politics.CATEGORY_ID: return NewsCategoryContainer.Politics.POLITICS_QUERY_STRINGS_EN;
            case NewsCategoryContainer.Finance.CATEGORY_ID: return NewsCategoryContainer.Finance.FINANCE_QUERY_STRINGS_EN;
            case NewsCategoryContainer.Movie.CATEGORY_ID: return NewsCategoryContainer.Movie.MOVIE_QUERY_STRINGS_EN;
            case NewsCategoryContainer.Food.CATEGORY_ID: return NewsCategoryContainer.Food.FOOD_QUERY_STRINGS_EN;
            case NewsCategoryContainer.Technology.CATEGORY_ID: return NewsCategoryContainer.Technology.TECHNOLOGY_QUERY_STRINGS_EN;
            default: return new String[]{"a"};
        }
    }
}
