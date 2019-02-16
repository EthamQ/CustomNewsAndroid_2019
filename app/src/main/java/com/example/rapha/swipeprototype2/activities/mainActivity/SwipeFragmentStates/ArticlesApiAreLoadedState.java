package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.questionCards.QuestionCardService;

public class ArticlesApiAreLoadedState extends SwipeFragmentState implements ISwipeFragmentState {

    public ArticlesApiAreLoadedState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "ArticlesApiAreLoadedState");
    }

    @Override
    public void handleAfterAddedToView() {
        if(swipeFragment.apiArticlesHaveBeenLoaded()){
            QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeFragment.swipeCardsList, swipeFragment.livekeyWords);
            swipeFragment.articlesArrayAdapter.notifyDataSetChanged();
            changeStateTo(new UserCanSwipeState(swipeFragment));
        }
    }

    @Override
    public void setCardsVisibility() {
        if(swipeFragment.apiArticlesHaveBeenLoaded()){
            swipeFragment.handleLoading(true, true, -1);
        }
    }

    @Override
    public void addArticlesToView() {
        swipeFragment.addArticlesToView(swipeFragment.apiArticlesToAdd);
    }

    @Override
    /**
     * Save all articles we just loaded from the api in the database.
     */
    public void saveArticlesInDb() {
//        if(swipeFragment != null){
//            if(swipeFragment.getActivity() != null){
//                NewsArticleDbService.getInstance(swipeFragment.getActivity().getApplication()).deleteAllSwipeArticles();
//                NewsArticleDbService.getInstance(swipeFragment.getActivity().getApplication())
//                        .insertNewsArticles(swipeFragment.apiArticlesToAdd);
//            }
//        }
    }

    @Override
    public void articlesFromApiAreLoaded() { }
    @Override
    public void handleArticlesOnEmpty() { }
    @Override
    public void loadArticles() { }
    @Override
    public void loadArticlesFromApi() { }
}
