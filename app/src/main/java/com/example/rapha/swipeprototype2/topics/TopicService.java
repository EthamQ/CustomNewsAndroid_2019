package com.example.rapha.swipeprototype2.topics;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.languages.TranslationService;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.LinkedList;
import java.util.List;

public class TopicService {

    /**
     * Returns the topics for the news category translated to the according language.
     * @param allTopics
     * @param newsCategory
     * @param languageId
     * @return
     */
    public static String[] getTopicsTranslated(List<KeyWordRoomModel> allTopics, NewsCategory newsCategory, int languageId){
        Log.d("topiccc", "getTopicsTranslated for language: " + languageId);
        Log.d("eeeee", "translate id: " + newsCategory.getCategoryID());
        switch(languageId){
            case LanguageSettingsService.INDEX_ENGLISH:
                return getTopicsForCategoryInEnglish(allTopics, newsCategory);
            case LanguageSettingsService.INDEX_GERMAN:
                return TranslationService.translateToGerman(
                    getTopicsForCategoryInEnglish(allTopics, newsCategory)
            );
            case LanguageSettingsService.INDEX_FRENCH:
                return TranslationService.translateToFrench(
                        getTopicsForCategoryInEnglish(allTopics, newsCategory)
                );
            case LanguageSettingsService.INDEX_RUSSIAN:
                return TranslationService.translateToRussian(
                    getTopicsForCategoryInEnglish(allTopics, newsCategory)
            );
            default: return getTopicsForCategoryInEnglish(allTopics, newsCategory);
        }
    }

    /**
     * Gets a list of all topics, retrieves the ones with the corresponding category and
     * translates them to the corresponding language.
     * @param allTopics
     * @param categoryId
     * @param languageId
     * @return
     */
    public static String[] getTopicsForCategory(List<KeyWordRoomModel> allTopics, int categoryId, int languageId){
        Log.d("iii", "getTopicsForCategory: " + categoryId);
        NewsCategory categoryToQuery = NewsCategoryContainer.getCategory(categoryId);
        String[] topicsTranslated = getTopicsTranslated(allTopics, categoryToQuery, languageId);
        return topicsTranslated;
    }

    /**
     * Returns all query Strings for a certain category in english.
     * It contains the default query strings for the category and the ones from the database
     * that the user liked or hasn't rated yet.
     * A NewsCategory object uses this logic.
     * @param allTopics
     * @param newsCategory
     * @return
     */
    public static String[] getTopicsForCategoryInEnglish(List<KeyWordRoomModel> allTopics, NewsCategory newsCategory){
        Log.d("eeeeee", "getTopicsForCategoryInEnglish: " + newsCategory.getCategoryID() + ", topics size: " + allTopics.size());
        // Get query strings that the user prefers or hasn't set yet.
        LinkedList<KeyWordRoomModel> keyWords = new LinkedList<>();
        for(int i = 0; i < allTopics.size(); i++){
            boolean keyWordBelongsToCategory = allTopics.get(i).categoryId == newsCategory.getCategoryID();
            boolean keyWordShouldBeAdded = !(allTopics.get(i).status == KeyWordRoomModel.DISLIKED);
            if(keyWordBelongsToCategory && keyWordShouldBeAdded){
                transformSingleTopicToMultipleWords(allTopics, keyWords, i);
            }
        }
        Log.d("eeeeee", "getTopicsForCategoryInEnglish return: " + newsCategory.getCategoryID() + ", topics size: " + allTopics.size());
        return  combineDefaultAndUserPreferredTopics(newsCategory, keyWords);
    }

    private static String[] combineDefaultAndUserPreferredTopics(NewsCategory newsCategory, LinkedList<KeyWordRoomModel> userPreferredKeyWords){
        Log.d("ffffff", "combineDefaultAndUserPreferredTopics1: " + newsCategory.getCategoryID());
        Log.d("ffffff", "combineDefaultAndUserPreferredTopics2: " + newsCategory.getCategoryID() +", "+ newsCategory.DEFAULT_QUERY_STRINGS_EN );
        // Combine user preferred and default query strings in one array.
        String[] queryWords = new String[userPreferredKeyWords.size() + newsCategory.DEFAULT_QUERY_STRINGS_EN.length];
        Log.d("ffffff", "combineDefaultAndUserPreferredTopics3: " + newsCategory.getCategoryID());
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


    private static void transformSingleTopicToMultipleWords(List<KeyWordRoomModel> allTopics, LinkedList<KeyWordRoomModel> keyWords, int index){
        TopicWordsTransformation queryWordTransformation = new TopicWordsTransformation();
        KeyWordRoomModel keyWordToAdd = allTopics.get(index);
        LinkedList<KeyWordRoomModel> transformedKeyWords = queryWordTransformation.transformQueryStrings(keyWordToAdd);
        for(int k = 0; k < transformedKeyWords.size(); k++){
            keyWords.add(transformedKeyWords.get(k));
        }
    }







    public static String getCurrentLanguageString(MainActivity mainActivity){
        boolean[] activeLanguages = LanguageSettingsService.loadChecked(mainActivity);
        return buildLanguageString(activeLanguages);
    }

    public static String buildLanguageString(boolean[] languages){
        StringBuilder builder = new StringBuilder();
        final int indexEN = LanguageSettingsService.INDEX_ENGLISH;
        final int indexGER = LanguageSettingsService.INDEX_GERMAN;
        final int indexFR = LanguageSettingsService.INDEX_FRENCH;
        final int indexRU = LanguageSettingsService.INDEX_RUSSIAN;
        String[] allLanguages = LanguageSettingsService.languageItems;
        for(int i = 0; i < allLanguages.length; i++){
            if(languages[indexEN]){
                builder.append(LanguageSettingsService.languageItems[indexEN]);
            }
            if(languages[indexGER]){
                builder.append(LanguageSettingsService.languageItems[indexGER]);
            }
            if(languages[indexFR]){
                builder.append(LanguageSettingsService.languageItems[indexFR]);
            }
            if(languages[indexRU]){
                builder.append(LanguageSettingsService.languageItems[indexRU]);
            }
        }

        // don't change order!
        if(languages[indexEN]){
            builder.append(LanguageSettingsService.languageItems[indexEN]);
        }
        if(languages[indexGER]){
            builder.append(LanguageSettingsService.languageItems[indexGER]);
        }
        if(languages[indexFR]){
            builder.append(LanguageSettingsService.languageItems[indexFR]);
        }
        if(languages[indexRU]){
            builder.append(LanguageSettingsService.languageItems[indexRU]);
        }
        return builder.toString();
    }

}
