package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.loading.SwipeLoadingService;
import com.example.rapha.swipeprototype2.questionCards.QuestionCardService;

public class AddDbArticlesToViewState extends SwipeFragmentState implements ISwipeFragmentState {

    public AddDbArticlesToViewState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "AddDbArticlesToViewState");
    }

    @Override
    /**
     * If we successfully loaded articles from the database then we add them to the swipe cards.
     * If not we go to LoadArticlesFromApiState immediately and let it load the articles.
     *
     */
    public void addArticlesToView() {

        if(swipeFragment.dbArticlesToAdd.size() > 0){
            Log.d("questioncard", "AddDbArticlesToViewState addArticlesToView()");
            swipeFragment.addArticlesToView(swipeFragment.dbArticlesToAdd);
            QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeFragment.swipeCardsList, swipeFragment.livekeyWords);
        }
        else{
            changeStateTo(new LoadArticlesFromApiState(swipeFragment));
            swipeFragment.swipeFragmentState.loadArticlesFromApi();
        }

    }

    @Override
    /**
     * Only gets called if we have available articles from the database.
     * Make the articles visible.
     */
    public void setCardsVisibility() {
        swipeFragment.handleLoading(true, true, -1);
    }

    /**
     * Load articles from the api after database articles have been added to the view.
     */
    @Override
    public void handleAfterAddedToView() {
        Log.d("questioncard", "DbArticlesAreAddedToViewState handleAfterAddedToView()");
        changeStateTo(new LoadArticlesFromApiState(swipeFragment));
        swipeFragment.swipeFragmentState.loadArticlesFromApi();
    }

    @Override
    public void articlesFromApiAreLoaded() { }
    @Override
    public void handleArticlesOnEmpty() { }
    @Override
    public void loadArticles() { }
    @Override
    public void loadArticlesFromApi() { }
    @Override
    public void saveArticlesInDb() { }

}
