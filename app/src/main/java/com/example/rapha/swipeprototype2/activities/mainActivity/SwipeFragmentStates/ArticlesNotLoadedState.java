package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;


import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesNotLoadedState extends MainActivityState implements IMainActivityState{

    public ArticlesNotLoadedState(SwipeFragment swipeFragment){
        super(swipeFragment);
    }

    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb){
        swipeFragment.loadArticles(preferencesInDb);
        changeStateTo(new WaitForArticlesState(swipeFragment));
    }

    @Override
    public void articlesAreLoaded() {

    }

    @Override
    public void addArticlesToView() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }


}
