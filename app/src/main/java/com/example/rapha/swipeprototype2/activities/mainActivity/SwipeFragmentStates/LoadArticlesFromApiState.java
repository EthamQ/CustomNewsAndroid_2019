package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class LoadArticlesFromApiState extends SwipeFragmentState implements ISwipeFragmentState {

    public LoadArticlesFromApiState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "LoadArticlesFromApiState");
    }

    @Override
    public void handleArticlesOnEmpty() {
        loadArticlesFromApi();
    }

    @Override
    /**
     * Check if enough swipe cards are available.
     * If yes go to the final UserCanSwipeState.
     * If no load articles from the api and go to the WaitForApiArticlesState.
     */
    public void loadArticlesFromApi() {
        if(swipeFragment.shouldRequestArticles()){
            swipeFragment.loadArticlesFromApi();
            changeStateTo(new WaitForApiArticlesState(swipeFragment));
        }
        else{
            changeStateTo(new UserCanSwipeState(swipeFragment));
        }
    }

    @Override
    public void saveArticlesInDb() { }
    @Override
    public void addArticlesToView() { }
    @Override
    public void articlesFromApiAreLoaded() { }
    @Override
    public void loadArticles() { }
    @Override
    public void setCardsVisibility() { }
    @Override
    public void handleAfterAddedToView() { }
}
