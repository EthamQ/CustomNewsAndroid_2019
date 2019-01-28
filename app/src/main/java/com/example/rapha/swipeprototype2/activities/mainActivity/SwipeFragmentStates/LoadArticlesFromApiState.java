package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

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
        // No cached articles, send api request.
        if(shouldRequestArticles()){
            swipeFragment.loadArticlesFromApi();
            changeStateTo(new WaitForApiArticlesState(swipeFragment));
        }
        // Enough cached articles go directly to the final state without requesting articles.
        else{
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

    }

    @Override
    public void loadArticles() {

    }
}
