package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesAreLoadedState extends MainActivityState implements IMainActivityState {

    public ArticlesAreLoadedState(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void addArticlesToView() {
        mainActivity.addArticlesToView();
        changeStateTo(new ArticlesAreAddedToViewState(mainActivity));
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void articlesAreLoaded() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }
}
