package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.time.ApiRequestTimeService;
import com.example.rapha.swipeprototype2.utils.ListService;

import java.util.ArrayList;

public class UserCanSwipeState extends SwipeFragmentState implements ISwipeFragmentState {
    public UserCanSwipeState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "UserCanSwipeState");
    }

    @Override
    /**
     * If we are about to run out of cards switch back to the LoadArticlesFromApiState
     * and let it handle reloading new cards.
     */
    public void handleArticlesOnEmpty() {
        if(ApiRequestTimeService.forceApiReload(swipeFragment.mainActivity)){
            swipeFragment.swipeCardsList = (ArrayList)ListService.removeAllEntriesStartingAt(swipeFragment.swipeCardsList, (swipeFragment.articlesAmountLoad + 1));
            changeStateTo(new LoadArticlesFromApiState(swipeFragment));
            }
        else if(swipeFragment.shouldRequestArticles()){
            changeStateTo(new LoadArticlesFromApiState(swipeFragment));
        }
    }

    @Override
    public void loadArticles() { }
    @Override
    public void setCardsVisibility() { }
    @Override
    public void handleAfterAddedToView() { }
    @Override
    public void loadArticlesFromApi() { }
    @Override
    public void saveArticlesInDb() { }
    @Override
    public void addArticlesToView() { }
    @Override
    public void articlesFromApiAreLoaded() { }
}
