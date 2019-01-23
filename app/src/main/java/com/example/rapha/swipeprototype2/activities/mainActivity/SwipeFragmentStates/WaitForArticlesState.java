package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;


import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.List;

public class WaitForArticlesState extends MainActivityState implements ISwipeFragmentState {

    public WaitForArticlesState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void articlesAreLoaded() {
        // add them to db
        swipeFragment.newsArticleDbService.deleteAll();
        swipeFragment.newsArticleDbService.insertNewsArticles(swipeFragment.newsArticlesToSwipe);
        changeStateTo(new ArticlesAreLoadedApiState(swipeFragment));
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void loadArticlesFromDB() {

    }

    @Override
    public void addArticlesToView() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }
}
