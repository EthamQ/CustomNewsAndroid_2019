package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.languages.TranslationService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.LinkedList;

public class NewsCategoryUtils {

    public static String[] getQueryStrings(SwipeFragment swipeFragment, int categoryId, int languageId){
        NewsCategory categoryToQuery = NewsCategoryContainer.getCategory(categoryId);
        return getQueryStringsTranslated(swipeFragment, categoryToQuery, languageId);
    }

    /**
     * Returns all query Strings for a certain category in english.
     * It contains the default query strings for the category and the ones from the database
     * that the user liked or hasn't rated yet.
     * A NewsCategory object uses this logic.
     * @param swipeFragment
     * @param newsCategory
     * @return
     */
    public static String[] getQueryStringsEnglish(SwipeFragment swipeFragment, NewsCategory newsCategory){
        // Get query strings that the user prefers or hasn't set yet.
        LinkedList<KeyWordRoomModel> keyWords = new LinkedList<>();
        for(int i = 0; i < swipeFragment.livekeyWords.size(); i++){
            boolean keyWordBelongsToCategory = swipeFragment.livekeyWords.get(i).categoryId == newsCategory.getCategoryID();
            boolean keyWordShouldBeAdded = !(swipeFragment.livekeyWords.get(i).status == KeyWordRoomModel.DISLIKED);
            if(keyWordBelongsToCategory && keyWordShouldBeAdded){
                   transformSingleToMultipleKeyWords(swipeFragment, keyWords, i);
            }
        }
        return  combineDefaultAndUserPreferredKeyWords(newsCategory, keyWords);
    }

    private static void transformSingleToMultipleKeyWords(SwipeFragment swipeFragment, LinkedList<KeyWordRoomModel> keyWords, int index){
        QueryWordTransformation queryWordTransformation = new QueryWordTransformation();
        KeyWordRoomModel keyWordToAdd = swipeFragment.livekeyWords.get(index);
        LinkedList<KeyWordRoomModel> transformedKeyWords = queryWordTransformation.transformQueryStrings(keyWordToAdd);
        for(int k = 0; k < transformedKeyWords.size(); k++){
            keyWords.add(transformedKeyWords.get(k));
        }
    }

    private static String[] combineDefaultAndUserPreferredKeyWords(NewsCategory newsCategory, LinkedList<KeyWordRoomModel> userPreferredKeyWords){
        // Combine user preferred and default query strings in one array.
        String[] queryWords = new String[userPreferredKeyWords.size() + newsCategory.DEFAULT_QUERY_STRINGS_EN.length];

        // Add user preferred query strings.
        for(int i = 0; i < userPreferredKeyWords.size(); i++){
            queryWords[i] = userPreferredKeyWords.get(i).keyWord;
        }

        // Add the default query strings.
        for(int i = 0; i < newsCategory.DEFAULT_QUERY_STRINGS_EN.length; i++){
            queryWords[i + userPreferredKeyWords.size()] = newsCategory.DEFAULT_QUERY_STRINGS_EN[i];
        }
        return  queryWords;
    }

    /**
     * Returns the query strings for the news category translated to the according language.
     * @param swipeFragment
     * @param newsCategory
     * @param languageId
     * @return
     */
    private static String[] getQueryStringsTranslated(SwipeFragment swipeFragment, NewsCategory newsCategory, int languageId){
        switch(languageId){
            case LanguageSettingsService
                    .INDEX_ENGLISH: return newsCategory.getQueryStringsEnglish(swipeFragment);
            case LanguageSettingsService
                    .INDEX_GERMAN: return TranslationService.translateToGerman(
                    newsCategory.getQueryStringsEnglish(swipeFragment)
            );
            case LanguageSettingsService
                    .INDEX_FRENCH: return newsCategory.getQueryStringsEnglish(swipeFragment);
            case LanguageSettingsService
                    .INDEX_RUSSIAN: return newsCategory.getQueryStringsEnglish(swipeFragment);
            default: return newsCategory.getQueryStringsEnglish(swipeFragment);
        }
    }

}
