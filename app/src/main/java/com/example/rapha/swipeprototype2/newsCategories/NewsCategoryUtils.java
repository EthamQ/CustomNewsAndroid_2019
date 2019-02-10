package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.IKeyWordProvider;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.languages.TranslationService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.LinkedList;

public class NewsCategoryUtils {

    public static String[] getQueryStrings(IKeyWordProvider keyWordProvider, int categoryId, int languageId){
        NewsCategory categoryToQuery = NewsCategoryContainer.getCategory(categoryId);
        return getQueryStringsTranslated(keyWordProvider, categoryToQuery, languageId);
    }

    /**
     * Returns all query Strings for a certain category in english.
     * It contains the default query strings for the category and the ones from the database
     * that the user liked or hasn't rated yet.
     * A NewsCategory object uses this logic.
     * @param keyWordProvider
     * @param newsCategory
     * @return
     */
    public static String[] getQueryStringsEnglish(IKeyWordProvider keyWordProvider, NewsCategory newsCategory){
        // Get query strings that the user prefers or hasn't set yet.
        LinkedList<KeyWordRoomModel> keyWords = new LinkedList<>();
        for(int i = 0; i < keyWordProvider.getCurrentKeyWords().size(); i++){
            boolean keyWordBelongsToCategory = keyWordProvider.getCurrentKeyWords().get(i).categoryId == newsCategory.getCategoryID();
            boolean keyWordShouldBeAdded = !(keyWordProvider.getCurrentKeyWords().get(i).status == KeyWordRoomModel.DISLIKED);
            if(keyWordBelongsToCategory && keyWordShouldBeAdded){
                   transformSingleToMultipleKeyWords(keyWordProvider, keyWords, i);
            }
        }
        return  combineDefaultAndUserPreferredKeyWords(newsCategory, keyWords);
    }

    private static void transformSingleToMultipleKeyWords(IKeyWordProvider keyWordProvider, LinkedList<KeyWordRoomModel> keyWords, int index){
        QueryWordTransformation queryWordTransformation = new QueryWordTransformation();
        KeyWordRoomModel keyWordToAdd = keyWordProvider.getCurrentKeyWords().get(index);
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
     * @param keyWordProvider
     * @param newsCategory
     * @param languageId
     * @return
     */
    private static String[] getQueryStringsTranslated(IKeyWordProvider keyWordProvider, NewsCategory newsCategory, int languageId){
        switch(languageId){
            case LanguageSettingsService
                    .INDEX_ENGLISH: return newsCategory.getQueryStringsEnglish(keyWordProvider);
            case LanguageSettingsService
                    .INDEX_GERMAN: return TranslationService.translateToGerman(
                    newsCategory.getQueryStringsEnglish(keyWordProvider)
            );
            case LanguageSettingsService
                    .INDEX_FRENCH: return newsCategory.getQueryStringsEnglish(keyWordProvider);
            case LanguageSettingsService
                    .INDEX_RUSSIAN: return newsCategory.getQueryStringsEnglish(keyWordProvider);
            default: return newsCategory.getQueryStringsEnglish(keyWordProvider);
        }
    }

}
