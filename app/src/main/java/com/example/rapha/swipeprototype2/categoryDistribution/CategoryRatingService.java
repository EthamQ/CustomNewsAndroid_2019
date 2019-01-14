package com.example.rapha.swipeprototype2.categoryDistribution;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.roomDatabase.DbService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class CategoryRatingService {

    // The rating mustn't go lower than this value.
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 30;

    /**
     * Increments the rating value of the category of swipedArticle by 1 in the database.
     * @param application
     * @param owner
     * @param swipedArticle The article the user swiped to the left or right in MainActivity.
     */
    public static void rateAsInteresting(Application application, LifecycleOwner owner, final NewsArticle swipedArticle){
        rate(application, owner, swipedArticle, true);
    }

    /**
     * Decrements the rating value of the category of swipedArticle by 1 in the database.
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
        // Get previous ratings to calculate the new one.
        dbService.getAllUserPreferences().observe(owner, new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> liveUserPreferences) {
                for(int i = 0; i < liveUserPreferences.size(); i++){
                    Log.d("FROMDB", "Rating: " + liveUserPreferences.get(i));
                    if(liveUserPreferences.get(i).getNewsCategoryId() == swipedArticle.newsCategory){
                        int newRating = liveUserPreferences.get(i).getRating();
                        if(interesting && newRating < MAX_RATING){
                            newRating++;
                        }
                        else if(newRating > MIN_RATING && newRating != MAX_RATING){
                            newRating--;
                        }
                        newsCategory.setRating(newRating);
                    }
                }
            }
        });

        // Update in database.
        dbService.updateUserPreference(new UserPreferenceRoomModel(
                swipedArticle.newsCategory,
                newsCategory.getRating()
        ));
    }
}
