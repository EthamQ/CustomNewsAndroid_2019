package com.example.rapha.swipeprototype2.roomDatabase;

import android.util.Log;

import com.example.rapha.swipeprototype2.categoryDistribution.CategoryRatingService;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRepository;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRepository;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;

import java.util.LinkedList;


public class FillDatabase {

    /**
     * Fill the table that contains the categories with its corresponding ratings
     * if it is empty.
     * @param repository
     */
    public static void fillCategories(UserPreferenceRepository repository){

        NewsCategoryContainer newsNewsCategoryContainer = new NewsCategoryContainer();
        for(NewsCategory category : newsNewsCategoryContainer.allCategories){
            Log.d("datapoint: ","fill in db: " + category.getCategoryID());
            repository.insert(new UserPreferenceRoomModel(
                    category.getCategoryID(),
                    CategoryRatingService.MIN_RATING));
        }

        repository.getAllUserPreferences().observeForever(cats ->{
            for(UserPreferenceRoomModel category : cats){
                Log.d("datapoint: ","observed after fill: " + category.getNewsCategoryId());
            }
            });
    }

    public static void fillKeyWords(KeyWordRepository repository){
        NewsCategoryContainer categoryContainer = new NewsCategoryContainer();
        LinkedList<NewsCategory> allCategories = categoryContainer.allCategories;
        for(int i = 0; i < allCategories.size(); i++){
            for(int j = 0; j < allCategories.get(i).USER_DETERMINED_QUERY_STRINGS_EN.length; j++){
                repository.insert(new KeyWordRoomModel(
                        allCategories.get(i).USER_DETERMINED_QUERY_STRINGS_EN[j],
                        allCategories.get(i).getCategoryID())
                );
            }
        }
    }
}
