package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;

import java.util.LinkedList;
import java.util.List;

public class InitialState extends MainActivityState implements ISwipeFragmentState{
    public InitialState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void loadArticlesFromApi(List<UserPreferenceRoomModel> preferencesInDb) {

    }

    @Override
    public void loadArticlesFromDB() {

//        swipeFragment.newsArticleDbService.getAllArticles().observe(swipeFragment.mainActivity, new Observer<List<NewsArticleRoomModel>>() {
//            @Override
//            public void onChanged(@Nullable List<NewsArticleRoomModel> newsArticleRoomModels) {
//                for(int i = 0; i < newsArticleRoomModels.size(); i++){
//                    Log.d("NEWSDB", newsArticleRoomModels.get(i).title);
//                }
//                swipeFragment.setArticlesToAddToView(swipeFragment.newsArticleDbService.createNewsArticleList(newsArticleRoomModels, 10));
//                swipeFragment.addArticlesToView();
//            }
//        });
            changeStateTo(new ArticlesAreLoadedDbState(swipeFragment));

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
