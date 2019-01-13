package com.example.rapha.swipeprototype2.activities.mainActivity;

import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class WaitForArticlesState extends MainActivityState implements IMainActivityState {

    public WaitForArticlesState(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void articlesAreLoaded() {
        mainActivity.mainActivityState = new ArticlesAreLoadedState(mainActivity);
    }

    @Override
    public void addArticlesToView() {

    }
}
