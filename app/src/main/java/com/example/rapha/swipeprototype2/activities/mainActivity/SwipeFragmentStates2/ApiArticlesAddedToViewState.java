package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates.ArticlesNotLoadedState;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.models.NewsArticle;

import java.util.LinkedList;

public class ApiArticlesAddedToViewState extends SwipeFragmentState implements ISwipeFragmentState {
    public ApiArticlesAddedToViewState(SwipeFragment swipeFragment) {
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
        if(swipeFragment.articlesArrayList.size() < SwipeFragment.articlesAmountReload){
            Log.d("AMOUNT", "Reload now");
            changeStateTo(new WaitForApiArticlesState(swipeFragment));
            swipeFragment.loadArticles(swipeFragment.liveCategoryRatings);
        }
    }
}
