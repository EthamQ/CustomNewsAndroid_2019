package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;


import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;


import java.util.List;

public class WaitForArticlesState extends MainActivityState implements IMainActivityState {

    public WaitForArticlesState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void articlesAreLoaded() {
        changeStateTo(new ArticlesAreLoadedState(swipeFragment));
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void addArticlesToView() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }
}
