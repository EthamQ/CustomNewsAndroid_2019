package com.raphael.rapha.myNews.newsCategories;

import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRoomModel;

import java.util.LinkedList;
import java.util.List;

public class NewsCategoryService {

    public static String getDisplayNameForCategory(int categoryId){
        final NewsCategoryContainer categories = new NewsCategoryContainer();
        for(NewsCategory category : categories.allCategories){
            if(categoryId == category.getNewsCategoryID()){
                return category.displayName;
            }
        }
        return "";
    }

    /**
     * Returns a NewsCategoryContainer whose NewsCategory objects have the same
     * categoryId + rating value combination as the entries in "userPreferenceRoomModels".
     * @return Return the NewsCategoryContainer.
     */
    public static NewsCategoryContainer retrieveAndSetCategoryRating(List<NewsCategoryRatingRoomModel> userPreferenceRoomModels){
        NewsCategoryContainer categories = new NewsCategoryContainer();
        setRatingForEachCategory(categories, userPreferenceRoomModels);
        return categories;
    }

    /**
     * Retrieve the rating value for each category from ratingRoomModels.
     * Set the rating for every category in the NewsCategoryContainer.
     * @param categories
     * @param ratingRoomModels
     */
    private static void setRatingForEachCategory(NewsCategoryContainer categories, List<NewsCategoryRatingRoomModel> ratingRoomModels){
        final LinkedList<NewsCategory> allCategories = categories.allCategories;
        for(NewsCategoryRatingRoomModel currentRatingFromDb : ratingRoomModels){
            for(NewsCategory newsCategory : allCategories){
                if(currentRatingFromDb.getNewsCategoryId() == newsCategory.getNewsCategoryID()){
                    newsCategory.setRating(currentRatingFromDb.getRating());
                }
            }
        }
    }

}
