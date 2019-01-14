package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class WaitForArticlesState extends MainActivityState implements IMainActivityState {

    public WaitForArticlesState(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void articlesAreLoaded() {
        changeStateTo(new ArticlesAreLoadedState(mainActivity));
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void addArticlesToView() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }
}
