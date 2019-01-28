package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class DBArticlesAddedToViewState extends SwipeFragmentState implements ISwipeFragmentState {

    public DBArticlesAddedToViewState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void setCardsVisibility() {
        swipeFragment.setCardsVisibility(true);
        changeStateTo(new LoadArticlesFromApiState(swipeFragment));
        swipeFragment.swipeFragmentState.loadArticlesFromApi();
    }

    @Override
    public void loadArticlesFromDb() {

    }

    @Override
    public void loadArticlesFromApi() {

    }

    @Override
    public void saveArticlesInDb() {

    }

    @Override
    public void addArticlesToView() {

    }

    @Override
    public void articlesFromApiAreLoaded() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }

    @Override
    public void loadArticles() {

    }
}
