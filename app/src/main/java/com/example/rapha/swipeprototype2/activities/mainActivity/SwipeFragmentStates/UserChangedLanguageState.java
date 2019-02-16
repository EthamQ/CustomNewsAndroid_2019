package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.loading.SwipeLoadingService;
import com.example.rapha.swipeprototype2.questionCards.QuestionCardService;

public class UserChangedLanguageState extends SwipeFragmentState implements ISwipeFragmentState {
    public UserChangedLanguageState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "UserChangedLanguageState");
    }

    @Override
    public void setCardsVisibility() {
        swipeFragment.handleLoading(true, true, SwipeLoadingService.CHANGE_LANGUAGE);
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
        Log.d("questioncard", "UserChangedLanguageState loadArticles()()");
        swipeFragment.handleLoading(false, true, SwipeLoadingService.CHANGE_LANGUAGE);
        swipeFragment.swipeCardsList.clear();
        swipeFragment.loadArticlesFromApi();
    }

    @Override
    public void handleAfterAddedToView() {
        Log.d("questioncard", "UserChangedLanguageState handleAfterAddedToView()");
        QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeFragment.swipeCardsList, swipeFragment.livekeyWords);
        changeStateTo(new UserCanSwipeState(swipeFragment));
    }

    @Override
    public void loadArticlesFromApi() { }
    @Override
    public void saveArticlesInDb() { }
    @Override
    public void articlesFromApiAreLoaded() { }
    @Override
    public void handleArticlesOnEmpty() {}
}
