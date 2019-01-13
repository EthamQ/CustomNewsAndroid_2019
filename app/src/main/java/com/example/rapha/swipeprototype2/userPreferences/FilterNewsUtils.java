package com.example.rapha.swipeprototype2.userPreferences;

import com.example.rapha.swipeprototype2.newsCategories.Finance;
import com.example.rapha.swipeprototype2.newsCategories.Food;
import com.example.rapha.swipeprototype2.newsCategories.Movie;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.Politics;
import com.example.rapha.swipeprototype2.newsCategories.Technology;
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
    public static UserPreference retrieveAndSetCategoryRating(final UserPreference userPreference, List<UserPreferenceRoomModel> userPreferenceRoomModels){
        final NewsCategory finance = userPreference.getCategories().finance;
        final NewsCategory politics = userPreference.getCategories().politics;
        final NewsCategory food = userPreference.getCategories().food;
        final NewsCategory movie = userPreference.getCategories().movie;
        final NewsCategory technology  = userPreference.getCategories().technology;

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

        return userPreference;
    }
}
