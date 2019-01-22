package com.example.rapha.swipeprototype2.categoryDistribution;


import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class CategoryRatingService {

    // The rating mustn't go lower than this value.
    private static final int MIN_RATING = 3;
    private static final int MAX_RATING = 30;

    /**
     * Increments the rating value of the category of swipedArticle by 1 in the database.
     * @param swipedArticle The article the user swiped to the left or right in MainActivity.
     */
    public static void rateAsInteresting(List<UserPreferenceRoomModel> liveUserPreferences, SwipeFragment swipeFragment, final NewsArticle swipedArticle){
        Log.d("RIGHTEXIT", "in rateAsInteresting ");
        rate(liveUserPreferences, swipeFragment, swipedArticle, true);
    }

    /**
     * Decrements the rating value of the category of swipedArticle by 1 in the database.
     * @param swipedArticle The article the user swiped to the left or right in MainActivity.
     */
    public static void rateAsNotInteresting(List<UserPreferenceRoomModel> liveUserPreferences, SwipeFragment swipeFragment, final NewsArticle swipedArticle){
        rate(liveUserPreferences, swipeFragment, swipedArticle, false);
    }

    /**
     * Common logic of rateAsInteresting() and rateAsNotInteresting().
     * Retrieve the old rating value from the database and increase or decrease
     * it based on the boolean "interesting".
     * @param swipedArticle
     * @param interesting
     */
    private static void rate(List<UserPreferenceRoomModel> liveUserPreferences, SwipeFragment swipeFragment, final NewsArticle swipedArticle, final boolean interesting){
        // Get previous ratings to calculate the new one.
                for(int i = 0; i < liveUserPreferences.size(); i++){
                    if(liveUserPreferences.get(i).getNewsCategoryId() == swipedArticle.newsCategory){
                        int newRating = liveUserPreferences.get(i).getRating();
                        if(interesting && (newRating < MAX_RATING)){
                            newRating++;
                        }
                        else if(!interesting && (newRating > MIN_RATING)){
                            newRating--;
                        }
                        // Update in database.
                        swipeFragment.dbService.updateUserPreference(new UserPreferenceRoomModel(
                                swipedArticle.newsCategory,
                                newRating
                        ));
                    }
                }



    }
}
