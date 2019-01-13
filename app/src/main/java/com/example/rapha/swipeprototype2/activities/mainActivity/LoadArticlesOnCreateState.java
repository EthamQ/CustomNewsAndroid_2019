package com.example.rapha.swipeprototype2.activities.mainActivity;

import android.util.Log;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class LoadArticlesOnCreateState extends MainActivityState implements IMainActivityState{

    public LoadArticlesOnCreateState(MainActivity mainActivity){
        super(mainActivity);
    }

    /**
     * Call loadArticles() in MainActivity for every user preference from the database.
     * @param preferencesInDb
     */
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb){
        for(int i = 0; i < preferencesInDb.size(); i++){
            Log.d("FROMDB", "Rating: " + preferencesInDb.get(i));
                mainActivity.loadArticles(preferencesInDb);
        }
        mainActivity.mainActivityState = new WaitForArticlesState(mainActivity);

    }

    @Override
    public void articlesAreLoaded() {

    }

    @Override
    public void addArticlesToView() {

    }


}
