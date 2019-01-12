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

    // The rating mustn't go lower than this value.
    private static final int MIN_RATING = 1;

    /**
     * Increments the rating value of the category of swipedArticle by 1.
     * @param application
     * @param owner
     * @param swipedArticle The article the user swiped to the left or right in MainActivity.
     */
    public static void rateAsInteresting(Application application, LifecycleOwner owner, final NewsArticle swipedArticle){
        rate(application, owner, swipedArticle, true);
    }

    /**
     * Decrements the rating value of the category of swipedArticle by 1.
     * @param application
     * @param owner
     * @param swipedArticle The article the user swiped to the left or right in MainActivity.
     */
    public static void rateAsNotInteresting(Application application, LifecycleOwner owner, final NewsArticle swipedArticle){
        rate(application, owner, swipedArticle, false);
    }

    /**
     * Common logic of rateAsInteresting() and rateAsNotInteresting().
     * Retrieve the old rating value from the database and increase or decrease
     * it based on the boolean "interesting".
     * @param application
     * @param owner
     * @param swipedArticle
     * @param interesting
     */
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
                        int newRating = liveUserPreferences.get(i).getRating();
                        if(interesting){
                            newRating++;
                        }
                        else if(newRating > MIN_RATING){
                            newRating--;
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
