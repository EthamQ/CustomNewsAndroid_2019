package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates;

import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public interface IMainActivityState {

    void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb);
    void articlesAreLoaded();
    void addArticlesToView();
    void handleArticlesOnEmpty();

}
