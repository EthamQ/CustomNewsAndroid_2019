package com.example.rapha.swipeprototype2.UserPreferences;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.rapha.swipeprototype2.categories.Finance;
import com.example.rapha.swipeprototype2.categories.Food;
import com.example.rapha.swipeprototype2.categories.Movie;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.categories.Politics;
import com.example.rapha.swipeprototype2.categories.Technology;
import com.example.rapha.swipeprototype2.roomDatabase.DbService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class FilterNewsUtils {

    /**
     * Retrieve the current rating values for each category from the database and store them
     * in "UserPreference".
     * @param userPreference
     * @param application
     * @param lifecycleOwner
     * @return
     */
    public static UserPreference retrieveAndSetCategoryRating(final UserPreference userPreference, Application application, LifecycleOwner lifecycleOwner){
        final NewsCategory finance = userPreference.getCategories().finance;
        final NewsCategory politics = userPreference.getCategories().politics;
        final NewsCategory food = userPreference.getCategories().food;
        final NewsCategory movie = userPreference.getCategories().movie;
        final NewsCategory technology  = userPreference.getCategories().technology;

        DbService.getInstance(application)
                .getAllUserPreferences()
                .observe(lifecycleOwner, new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> userPreferenceRoomModels) {
                for(int i = 0; i< userPreferenceRoomModels.size(); i++){
                    UserPreferenceRoomModel currentPreference = userPreferenceRoomModels.get(i);
                    int currentRating = currentPreference.getRating();
                    int currentNewsCategory = currentPreference.getNewsCategoryId();
                    switch(currentNewsCategory){
                        case Finance.CATEGORY_ID:
                            finance.setRating(currentRating);
                            break;
                        case Politics.CATEGORY_ID:
                            politics.setRating(currentRating);
                            break;
                        case Food.CATEGORY_ID:
                            food.setRating(currentRating);
                            break;
                        case Movie.CATEGORY_ID:
                            movie.setRating(currentRating);
                            break;
                        case Technology.CATEGORY_ID:
                            technology.setRating(currentRating);
                            break;
                    }
                }
            }
        });
        return userPreference;
    }
}
