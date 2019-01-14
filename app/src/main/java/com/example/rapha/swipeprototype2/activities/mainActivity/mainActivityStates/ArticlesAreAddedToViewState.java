package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates;

import android.util.Log;

import com.example.rapha.swipeprototype2.Logging;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesAreAddedToViewState extends MainActivityState implements IMainActivityState {

    public ArticlesAreAddedToViewState(MainActivity mainActivity) {
        super(mainActivity);
    }

    /**
     * When the user swiped away almost all of the items
     * then the state goes back to ArticlesNotLoadedState and loads
     * new articles.
     */
    @Override
    public void handleArticlesOnEmpty() {
        Logging.logArticlesLeft(mainActivity);
        if(mainActivity.articlesArrayList.size() < MainActivity.articlesAmountReload){
            Log.d("AMOUNT", "Reload now");
            changeStateTo(new ArticlesNotLoadedState(mainActivity));
            mainActivity.loadArticles(mainActivity.liveUserPreferences);
        }
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void articlesAreLoaded() {

    }

    @Override
    public void addArticlesToView() {

    }

}
