package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.models.NewsArticle;

import java.util.LinkedList;

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
        swipeFragment.loadArticles(swipeFragment.liveCategoryRatings);
        changeStateTo(new WaitForApiArticlesState(swipeFragment));
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
}
