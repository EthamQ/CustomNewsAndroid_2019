package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class LoadArticlesFromApiState extends SwipeFragmentState implements ISwipeFragmentState {

    public LoadArticlesFromApiState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "LoadArticlesFromApiState");
    }

    @Override
    public void setCardsVisibility() {

    }

    @Override
    public void loadArticlesFromDb() {

    }

    @Override
    public void loadArticlesFromApi() {
        Log.d("uuu", "LoadArticlesFromApiState: loadArticlesFromApi()");
        // No cached articles, send api request.
        if(shouldRequestArticles()){
            Log.d("uuu", "LoadArticlesFromApiState: shouldRequestArticles yes");
            swipeFragment.loadArticlesFromApi();
            changeStateTo(new WaitForApiArticlesState(swipeFragment));
        }
        // Enough cached articles go directly to the final state without requesting articles.
        else{
            Log.d("uuu", "LoadArticlesFromApiState: shouldRequestArticles no");
            changeStateTo(new ApiArticlesAddedToViewState(swipeFragment));
        }
    }

    private boolean shouldRequestArticles(){
        return swipeFragment.articlesArrayList.size() < swipeFragment.articlesAmountNoNeedToLoad;
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
            loadArticlesFromApi();
    }

    @Override
    public void loadArticles() {

    }
}
