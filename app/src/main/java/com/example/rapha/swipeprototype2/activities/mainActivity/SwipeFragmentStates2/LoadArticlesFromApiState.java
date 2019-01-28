package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class LoadArticlesFromApiState extends SwipeFragmentState implements ISwipeFragmentState {

    public LoadArticlesFromApiState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void setCardsVisibility() {

    }

    @Override
    public void loadArticlesFromDb() {

    }

    @Override
    public void loadArticlesFromApi() {
        Log.d("newstate", "LoadArticlesFromApiState: loadArticlesFromApi()");
        swipeFragment.loadArticlesFromApi();
        changeStateTo(new WaitForApiArticlesState(swipeFragment));
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
