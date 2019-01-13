package com.example.rapha.swipeprototype2.activities.mainActivity;

import android.util.Log;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.List;

public class ArticlesAreLoadedState extends MainActivityState implements IMainActivityState {

    public ArticlesAreLoadedState(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void articlesAreLoaded() {

    }

    @Override
    public void addArticlesToView() {
        mainActivity.articlesArrayList.addAll(mainActivity.newsArticlesNewsApi);
        // Pseudo functionality to show when the articles are loaded.
        TextView tv = mainActivity.findViewById(R.id.helloText);
        tv.setText("Articles loaded, start to swipe");
        mainActivity.articlesArrayAdapter.notifyDataSetChanged();

        // ugly quick testing
        int amount0 = 0;
        int amount1 = 0;
        int amount2 = 0;
        int amount3 = 0;
        int amount4 = 0;
        for(int i = 0; i< mainActivity.newsArticlesNewsApi.size(); i++){
            Log.d("§§§", mainActivity.newsArticlesNewsApi.get(i).toString());
            if(mainActivity.newsArticlesNewsApi.get(i).newsCategory == 0){
                amount0++;
            }
            if(mainActivity.newsArticlesNewsApi.get(i).newsCategory == 1){
                amount1++;
            }
            if(mainActivity.newsArticlesNewsApi.get(i).newsCategory == 2){
                amount2++;
            }
            if(mainActivity.newsArticlesNewsApi.get(i).newsCategory == 3){
                amount3++;
            }
            if(mainActivity.newsArticlesNewsApi.get(i).newsCategory == 4){
                amount4++;
            }
        }
        Log.d("&&&", "0: " + amount0 + "\n"
                + "1: " + amount1 + "\n"
                + "2: " + amount2 + "\n"
                + "3: " + amount3 + "\n"
                + "4: " + amount4 + "\n");
    }
}
