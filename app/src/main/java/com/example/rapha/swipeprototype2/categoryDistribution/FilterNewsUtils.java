package com.example.rapha.swipeprototype2.categoryDistribution;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class FilterNewsUtils {

    /**
     * Retrieve the current rating values for each category and store them
     * in "UserPreference". The rating values are in userPreference
     * @return
     */
    public static NewsCategoryContainer retrieveAndSetCategoryRating(List<UserPreferenceRoomModel> userPreferenceRoomModels){
        NewsCategoryContainer categories = new NewsCategoryContainer();
        final NewsCategory finance = categories.finance;
        final NewsCategory politics = categories.politics;
        final NewsCategory food = categories.food;
        final NewsCategory movie = categories.movie;
        final NewsCategory technology  = categories.technology;

                for(int i = 0; i< userPreferenceRoomModels.size(); i++){
                    UserPreferenceRoomModel currentPreference = userPreferenceRoomModels.get(i);
                    int currentRating = currentPreference.getRating();
                    int currentNewsCategory = currentPreference.getNewsCategoryId();
                    switch(currentNewsCategory){
                        case NewsCategoryContainer.Finance.CATEGORY_ID:
                            finance.setRating(currentRating);
                            break;
                        case NewsCategoryContainer.Politics.CATEGORY_ID:
                            politics.setRating(currentRating);
                            break;
                        case NewsCategoryContainer.Food.CATEGORY_ID:
                            food.setRating(currentRating);
                            break;
                        case NewsCategoryContainer.Movie.CATEGORY_ID:
                            movie.setRating(currentRating);
                            break;
                        case NewsCategoryContainer.Technology.CATEGORY_ID:
                            technology.setRating(currentRating);
                            break;
                    }
                }

        return categories;
    }
}
