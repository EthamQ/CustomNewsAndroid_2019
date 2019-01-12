package com.example.rapha.swipeprototype2.UserPreferences;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.MainActivity;
import com.example.rapha.swipeprototype2.categories.Finance;
import com.example.rapha.swipeprototype2.categories.Food;
import com.example.rapha.swipeprototype2.categories.Movie;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.categories.Politics;
import com.example.rapha.swipeprototype2.categories.Technology;
import com.example.rapha.swipeprototype2.roomDatabase.DbService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;
import java.util.Observable;

public class FilterNewsUtils {

    /**
     * Retrieve the current rating values for each category from the database and store them
     * in "UserPreference".
     * @param userPreference
     * @return
     */
    public static void retrieveAndSetCategoryRating(final UserPreference userPreference, final MainActivity mainActivity)throws Exception{
        final NewsCategory finance = userPreference.getCategories().finance;
        final NewsCategory politics = userPreference.getCategories().politics;
        final NewsCategory food = userPreference.getCategories().food;
        final NewsCategory movie = userPreference.getCategories().movie;
        final NewsCategory technology  = userPreference.getCategories().technology;

        DbService.getInstance(mainActivity.getApplication())
                .getAllUserPreferences()
                .observe(mainActivity, new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> userPreferenceRoomModels) {
                for(int i = 0; i< userPreferenceRoomModels.size(); i++){
                    UserPreferenceRoomModel currentPreference = userPreferenceRoomModels.get(i);
                    Log.d("===", "RealData: " + currentPreference);
                    int currentRating = currentPreference.getRating();
                    int currentNewsCategory = currentPreference.getNewsCategoryId();
                    switch(currentNewsCategory){
                        case Finance.CATEGORY_ID:
                            Log.d("===", "case finance: " + userPreference);
                            finance.setRating(currentRating);
                            break;
                        case Politics.CATEGORY_ID:
                            Log.d("===", "case politics: " + userPreference);
                            politics.setRating(currentRating);
                            break;
                        case Food.CATEGORY_ID:
                            Log.d("===", "case food: " + userPreference);
                            food.setRating(currentRating);
                            break;
                        case Movie.CATEGORY_ID:
                            Log.d("===", "case movie: " + userPreference);
                            movie.setRating(currentRating);
                            break;
                        case Technology.CATEGORY_ID:
                            Log.d("===", "case technology: " + userPreference);
                            technology.setRating(currentRating);
                            break;
                    }

                }

                try {
                    FilterNewsService.callbackFunction(userPreference, mainActivity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
