package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesAreLoadedDbState extends MainActivityState implements ISwipeFragmentState {
    public ArticlesAreLoadedDbState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {
        swipeFragment.loadArticles(preferencesInDb);
        changeStateTo(new WaitForArticlesState(swipeFragment));
    }

    @Override
    public void loadArticlesFromDB() {

    }

    @Override
    public void articlesAreLoaded() {

    }

    @Override
    public void addArticlesToView() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }
}
