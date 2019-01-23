package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import com.example.rapha.swipeprototype2.models.NewsArticle;

import java.util.LinkedList;

public interface ISwipeFragmentState {

    void setCardsVisibility();
    void loadArticlesFromDb();
    void loadArticlesFromApi();
    void saveArticlesInDb();
    void addArticlesToView(LinkedList<NewsArticle> articlesToAdd);

}
