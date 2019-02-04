package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class UserChangedLanguageState extends SwipeFragmentState implements ISwipeFragmentState {
    public UserChangedLanguageState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "UserChangedLanguageState");
    }

    @Override
    public void setCardsVisibility() {
        swipeFragment.setCardsVisibility(true);
        changeStateTo(new UserCanSwipeState(swipeFragment));
    }

    @Override
    public void addArticlesToView() {
        swipeFragment.addArticlesToView(swipeFragment.apiArticlesToAdd);
    }

    @Override
    /**
     * Remove almost all of the swipe cards and load new ones
     * (with the correct language).
     * Make the view invisible while loading new data.
     */
    public void loadArticles() {
        swipeFragment.setCardsVisibility(false);
        for(int i = swipeFragment.swipeCardsList.size() - 1; i > 0; i--){
            swipeFragment.swipeCardsList.remove(i);
        }
        swipeFragment.loadArticlesFromApi();
    }

    @Override
    public void handleAfterAddedToView() { }
    @Override
    public void loadArticlesFromApi() { }
    @Override
    public void saveArticlesInDb() { }
    @Override
    public void articlesFromApiAreLoaded() { }
    @Override
    public void handleArticlesOnEmpty() {}
}
