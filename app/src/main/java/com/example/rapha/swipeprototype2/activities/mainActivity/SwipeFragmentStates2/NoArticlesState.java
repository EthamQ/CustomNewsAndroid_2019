package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;

import java.util.LinkedList;
import java.util.List;

public class NoArticlesState extends SwipeFragmentState implements ISwipeFragmentState {
    public NoArticlesState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void setCardsVisibility() {
        swipeFragment.setCardsVisibility(false);
    }

    @Override
    public void loadArticlesFromDb() {
        final NewsArticleDbService newsArticleDbService = NewsArticleDbService.getInstance(swipeFragment.getActivity().getApplication());
        newsArticleDbService.getAllArticles().observe(swipeFragment.getActivity(), new Observer<List<NewsArticleRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<NewsArticleRoomModel> articleModels) {
//                for(int i = 0; i < userPreferenceRoomModels.size(); i++){
//                    Log.d("RATINGLIVE", userPreferenceRoomModels.get(i).toString());
//                }
                swipeFragment.dbArticlesToAdd.addAll(newsArticleDbService.createNewsArticleList(articleModels, 10));
                changeStateTo(new AddArticlesToViewState(swipeFragment));
            }
        });
    }

    @Override
    public void loadArticlesFromApi() {

    }

    @Override
    public void saveArticlesInDb() {

    }

    @Override
    public void addArticlesToView(LinkedList<NewsArticle> articlesToAdd) {

    }
}
