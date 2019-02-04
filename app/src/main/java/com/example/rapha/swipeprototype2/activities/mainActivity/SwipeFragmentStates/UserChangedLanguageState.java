package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

import java.util.ArrayList;

public class UserChangedLanguageState extends SwipeFragmentState implements ISwipeFragmentState {
    public UserChangedLanguageState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "UserChangedLanguageState");
    }

    @Override
    public void setCardsVisibility() {
        swipeFragment.setCardsVisibility(true);
        changeStateTo(new ApiArticlesAddedToViewState(swipeFragment));
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
        swipeFragment.addArticlesToView(swipeFragment.apiArticlesToAdd);
    }

    @Override
    public void articlesFromApiAreLoaded() {

    }

    @Override
    public void handleArticlesOnEmpty() {
    }

    @Override
    public void loadArticles() {
        swipeFragment.setCardsVisibility(false);
        for(int i = swipeFragment.articlesArrayList.size() - 1; i > 1; i--){
            swipeFragment.articlesArrayList.remove(i);
        }
        swipeFragment.loadArticlesFromApi();
    }
}
