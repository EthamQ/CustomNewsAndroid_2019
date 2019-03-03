package com.raphael.rapha.myNews.topics;

import android.util.Log;

import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.languages.TranslationService;
import com.raphael.rapha.myNews.newsCategories.NewsCategory;
import com.raphael.rapha.myNews.newsCategories.NewsCategoryContainer;
import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;

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
    public static String[] getTopicsTranslated(List<TopicRoomModel> allTopics, NewsCategory newsCategory, int languageId){
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
    public static String[] getTopicsForCategory(List<TopicRoomModel> allTopics, int categoryId, int languageId){
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
    public static String[] getTopicsForCategoryInEnglish(List<TopicRoomModel> allTopics, NewsCategory newsCategory){
        // Get query strings that the user prefers or hasn't set yet.
        LinkedList<TopicRoomModel> keyWords = new LinkedList<>();
        for(int i = 0; i < allTopics.size(); i++){
            boolean keyWordBelongsToCategory = allTopics.get(i).categoryId == newsCategory.getNewsCategoryID();
            boolean keyWordShouldBeAdded = !(allTopics.get(i).status == TopicRoomModel.DISLIKED);
            if(keyWordBelongsToCategory && keyWordShouldBeAdded){
                transformSingleTopicToMultipleWords(allTopics, keyWords, i);
            }
        }
        return  combineDefaultAndUserPreferredTopics(newsCategory, keyWords);
    }

    private static String[] combineDefaultAndUserPreferredTopics(NewsCategory newsCategory, LinkedList<TopicRoomModel> userPreferredKeyWords){
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


    private static void transformSingleTopicToMultipleWords(List<TopicRoomModel> allTopics, LinkedList<TopicRoomModel> keyWords, int index){
        TopicWordsTransformation queryWordTransformation = new TopicWordsTransformation();
        TopicRoomModel keyWordToAdd = allTopics.get(index);
        LinkedList<TopicRoomModel> transformedKeyWords = queryWordTransformation.transformQueryStrings(keyWordToAdd);
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
