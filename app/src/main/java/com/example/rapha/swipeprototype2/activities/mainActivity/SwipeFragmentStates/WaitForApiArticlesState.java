package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class WaitForApiArticlesState extends SwipeFragmentState implements ISwipeFragmentState {
    public WaitForApiArticlesState(SwipeFragment swipeFragment) {
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
        changeStateTo(new ArticlesApiAreLoadedState(swipeFragment));
    }

    @Override
    public void handleArticlesOnEmpty() {

    }

    @Override
    public void loadArticles() {

    }
}
