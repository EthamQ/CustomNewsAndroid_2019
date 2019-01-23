package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.List;

public interface ISwipeFragmentState {

    void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb);
    void loadArticlesFromDB();
    void articlesAreLoaded();
    void addArticlesToView();
    void handleArticlesOnEmpty();

}
