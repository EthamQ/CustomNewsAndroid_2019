package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.languages.TranslationService;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.LinkedList;

public class NewsCategoryUtils {

    public static String[] getQueryWords(SwipeFragment swipeFragment, int categoryId, int languageId){
        NewsCategory categoryToQuery = NewsCategoryContainer.getCategory(categoryId);
        return getLanguageQueryString(swipeFragment, categoryToQuery, languageId);
    }

    private static String[] getLanguageQueryString(SwipeFragment swipeFragment, NewsCategory newsCategory, int languageId){
        switch(languageId){
            case LanguageSettingsService
                    .INDEX_ENGLISH: return newsCategory.getQueryStrings_EN(swipeFragment);
            case LanguageSettingsService
                    .INDEX_GERMAN: return TranslationService.translateToGerman(
                            newsCategory.getQueryStrings_EN(swipeFragment)
            );
            case LanguageSettingsService
                    .INDEX_FRENCH: return newsCategory.getQueryStrings_EN(swipeFragment);
            case LanguageSettingsService
                    .INDEX_RUSSIAN: return newsCategory.getQueryStrings_EN(swipeFragment);
            default: return newsCategory.getQueryStrings_EN(swipeFragment);
        }
    }

}
