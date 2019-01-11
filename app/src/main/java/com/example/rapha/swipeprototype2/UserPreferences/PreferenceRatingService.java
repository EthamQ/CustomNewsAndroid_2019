package com.example.rapha.swipeprototype2.UserPreferences;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.roomDatabase.DbService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class PreferenceRatingService {
    public static void rateAsInteresting(Application application, LifecycleOwner owner, final NewsArticle swipedArticle){
        rate(application, owner, swipedArticle, true);
    }

    public static void rateAsNotInteresting(Application application, LifecycleOwner owner, final NewsArticle swipedArticle){
        rate(application, owner, swipedArticle, false);
    }

    private static void rate(Application application, LifecycleOwner owner, final NewsArticle swipedArticle, final boolean interesting){
        final DbService dbService = DbService.getInstance(application);
        final NewsCategory newsCategory = new NewsCategory();
        dbService.getAllUserPreferences().observe(owner, new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> liveUserPreferences) {
                for(int i = 0; i < liveUserPreferences.size(); i++){
                    Log.d("FROMDB", "Rating: " + liveUserPreferences.get(i));
                    //ur.deleteAll();
                    if(liveUserPreferences.get(i).getNewsCategoryId() == swipedArticle.newsCategory){
                        int newRating;
                        if(interesting){
                            newRating = liveUserPreferences.get(i).getRating() + 1;
                        }
                        else{
                            newRating = liveUserPreferences.get(i).getRating() - 1;
                        }
                        newsCategory.setRating(newRating);
                    }
                }
            }
        });
        dbService.updateUserPreference(new UserPreferenceRoomModel(
                swipedArticle.newsCategory,
                newsCategory.getRating()
        ));
    }
}
