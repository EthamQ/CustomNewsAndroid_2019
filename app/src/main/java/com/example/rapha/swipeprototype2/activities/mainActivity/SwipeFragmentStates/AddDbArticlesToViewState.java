package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class AddDbArticlesToViewState extends SwipeFragmentState implements ISwipeFragmentState {

    public AddDbArticlesToViewState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "AddDbArticlesToViewState");
    }

    @Override
    public void setCardsVisibility() {


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
        Log.d("newstate", "AddDbArticlesToViewState: addArticlesToView(), amount of db articles: " + swipeFragment.dbArticlesToAdd.size());
        if(swipeFragment.dbArticlesToAdd.size() > 0){
            changeStateTo(new DBArticlesAddedToViewState(swipeFragment));
            swipeFragment.addArticlesToView(swipeFragment.dbArticlesToAdd);
        }
        else{
            changeStateTo(new LoadArticlesFromApiState(swipeFragment));
            swipeFragment.swipeFragmentState.loadArticlesFromApi();
        }
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
