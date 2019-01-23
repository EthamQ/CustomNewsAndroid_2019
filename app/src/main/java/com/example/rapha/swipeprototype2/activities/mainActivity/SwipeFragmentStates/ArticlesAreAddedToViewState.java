package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;


import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesAreAddedToViewState extends MainActivityState implements ISwipeFragmentState {

    public ArticlesAreAddedToViewState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    /**
     * When the user swiped away almost all of the items
     * then the state goes back to ArticlesNotLoadedState and loads
     * new articles.
     */
    @Override
    public void handleArticlesOnEmpty() {
        if(swipeFragment.articlesArrayList.size() < SwipeFragment.articlesAmountReload){
            Log.d("AMOUNT", "Reload now");
            changeStateTo(new InitialState(swipeFragment));
            swipeFragment.loadArticles(swipeFragment.liveUserPreferences);
        }
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

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

}
