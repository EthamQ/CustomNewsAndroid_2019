package com.example.rapha.swipeprototype2.utils;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.languageSettings.LanguageSettingsService;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;

public class CategoryUtils {

    public static String[] getQueryWords(SwipeFragment swipeFragment, int categoryId, int languageId){
        NewsCategory categoryToQuery = NewsCategoryContainer.getCategory(categoryId);
        return getLanguageQueryString(swipeFragment, categoryToQuery, languageId);
    }

    public static String[] getLanguageQueryString(SwipeFragment swipeFragment, NewsCategory newsCategory, int languageId){
        switch(languageId){
            case LanguageSettingsService
                    .INDEX_ENGLISH: return newsCategory.getQueryStrings_EN(swipeFragment);
            case LanguageSettingsService
                    .INDEX_GERMAN: return newsCategory.QUERY_STRINGS_GER;
            case LanguageSettingsService
                    .INDEX_FRENCH: return newsCategory.QUERY_STRINGS_FR;
            case LanguageSettingsService
                    .INDEX_RUSSIAN: return newsCategory.QUERY_STRINGS_RU;
            default: return newsCategory.QUERY_STRINGS_EN;
        }
    }
}
