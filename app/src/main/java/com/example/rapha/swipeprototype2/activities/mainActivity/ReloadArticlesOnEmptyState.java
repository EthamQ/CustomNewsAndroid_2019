package com.example.rapha.swipeprototype2.activities.mainActivity;

import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class ReloadArticlesOnEmptyState extends MainActivityState implements IMainActivityState {

    public ReloadArticlesOnEmptyState(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void articlesAreLoaded() {

    }

    @Override
    public void addArticlesToView() {

    }
}
