package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;

import java.util.LinkedList;

public class ArticlesDbAreLoadedState extends SwipeFragmentState implements ISwipeFragmentState {

    public ArticlesDbAreLoadedState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void setCardsVisibility() {
        if(swipeFragment.apiArticlesToAdd.size() > 0){
            swipeFragment.setCardsVisibility(true);
        }

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

    }
}
