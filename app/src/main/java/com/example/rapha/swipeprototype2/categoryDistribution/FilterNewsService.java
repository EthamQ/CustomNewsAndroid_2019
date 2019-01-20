package com.example.rapha.swipeprototype2.categoryDistribution;

import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

public class FilterNewsService {

    /**
     * Calculates the amount for every news category that should be requested from the api.
     * @param userPreferenceRoomModels
     * @return A DistributionContainer containing the distribution for every single news category.
     */
    public static DistributionContainer getCategoryDistribution(List<UserPreferenceRoomModel> userPreferenceRoomModels){
        LinkedList<Distribution> distribution =
                FilterNewsUtils.retrieveAndSetCategoryRating(userPreferenceRoomModels).getCategoryDistribution();
        DistributionContainer distributionContainer = new DistributionContainer();
        distributionContainer.setDistribution(distribution);
        return distributionContainer;
    }

}
