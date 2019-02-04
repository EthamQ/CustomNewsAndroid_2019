package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

public interface ISwipeFragmentState {

    void setCardsVisibility();
    void handleAfterAddedToView();
    void loadArticlesFromApi();
    void saveArticlesInDb();
    void addArticlesToView();
    void articlesFromApiAreLoaded();
    void handleArticlesOnEmpty();
    void loadArticles();

}
