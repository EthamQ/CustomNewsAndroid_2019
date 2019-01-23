package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesAreLoadedState extends MainActivityState implements IMainActivityState {

    public ArticlesAreLoadedState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void addArticlesToView() {
        swipeFragment.addArticlesToView();
        changeStateTo(new ArticlesAreAddedToViewState(swipeFragment));
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
