package com.example.rapha.swipeprototype2.categoryDistribution;

import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

public class FilterNewsService {

    public static final int MAX_NUMBER_OF_ARTICLES = 100;

    public static DistributionContainer getCategoryDistribution(List<UserPreferenceRoomModel> userPreferenceRoomModels){
        LinkedList<Distribution> distribution =
                FilterNewsUtils.retrieveAndSetCategoryRating(userPreferenceRoomModels).getCategoryDistribution();
        DistributionContainer distributionContainer = new DistributionContainer();
        distributionContainer.setDistribution(distribution);
        return distributionContainer;
    }

}
