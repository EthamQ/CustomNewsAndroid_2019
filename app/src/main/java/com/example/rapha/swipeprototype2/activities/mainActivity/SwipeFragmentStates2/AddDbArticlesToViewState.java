package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.models.NewsArticle;

import java.util.LinkedList;

public class AddDbArticlesToViewState extends SwipeFragmentState implements ISwipeFragmentState {

    public AddDbArticlesToViewState(SwipeFragment swipeFragment) {
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

        if(swipeFragment.dbArticlesToAdd.size() > 0){
            changeStateTo(new DBArticlesAddedToViewState(swipeFragment));
            swipeFragment.addArticlesToView(swipeFragment.dbArticlesToAdd);
        }
        else{
            changeStateTo(new LoadArticlesFromApiState(swipeFragment));
        }
    }

    @Override
    public void articlesFromApiAreLoaded() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }

}
