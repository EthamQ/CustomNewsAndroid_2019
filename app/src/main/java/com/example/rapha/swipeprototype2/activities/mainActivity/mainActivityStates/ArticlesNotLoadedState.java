package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesNotLoadedState extends MainActivityState implements IMainActivityState{

    public ArticlesNotLoadedState(MainActivity mainActivity){
        super(mainActivity);
    }

    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb){
        mainActivity.loadArticles(preferencesInDb);
        changeStateTo(new WaitForArticlesState(mainActivity));
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
