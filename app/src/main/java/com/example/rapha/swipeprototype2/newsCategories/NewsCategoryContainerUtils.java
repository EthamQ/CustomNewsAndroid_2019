package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.LinkedList;

public class NewsCategoryContainerUtils {

    /**
     * Calculates how many news articles for the news category should be requested
     * by using its rating, the total rating for all categories and the total number
     * of requested articles.
     * @param newsCategory
     * @param totalRating
     * @return
     */
    public static int calculateDistribution(NewsCategory newsCategory, int totalRating){
        double percentage = ((double)newsCategory.getRating() / (double)totalRating);
        int totalAmount = ApiService.MAX_NUMBER_OF_ARTICLES;
        int result = (int)(percentage * totalAmount);
        return result;
    }

    public static String[] getQueryStrings_EN(SwipeFragment swipeFragment, NewsCategory newsCategory){
        // Get query strings that the user prefers or hasn't set yet.
        LinkedList<KeyWordRoomModel> keyWords = new LinkedList<>();
        for(int i = 0; i < swipeFragment.livekeyWords.size(); i++){
            if(swipeFragment.livekeyWords.get(i).categoryId == newsCategory.getCategoryID()){
                if(swipeFragment.livekeyWords.get(i).status == KeyWordRoomModel.UNSET
                        || swipeFragment.livekeyWords.get(i).status == KeyWordRoomModel.LIKED){
                    keyWords.add(swipeFragment.livekeyWords.get(i));
                }
            }
        }

        // Combine user preferred and default query strings in one array.
        String[] queryWords = new String[keyWords.size() + newsCategory.DEFAULT_QUERY_STRINGS_EN.length];

        // Add user preferred query strings.
        for(int i = 0; i < keyWords.size(); i++){
            queryWords[i] = keyWords.get(i).keyWord;
        }

        // Add the default query strings.
        for(int i = 0; i < newsCategory.DEFAULT_QUERY_STRINGS_EN.length; i++){
            queryWords[i + keyWords.size()] = newsCategory.DEFAULT_QUERY_STRINGS_EN[i];
        }
        return  queryWords;
    }
}

