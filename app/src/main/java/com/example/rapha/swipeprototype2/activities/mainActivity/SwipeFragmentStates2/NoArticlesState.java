package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
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
                swipeFragment.dbArticlesToAdd.addAll(newsArticleDbService.createNewsArticleList(articleModels, 10));
                changeStateTo(new AddDbArticlesToViewState(swipeFragment));
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
    public void addArticlesToView() {

    }

    @Override
    public void articlesFromApiAreLoaded() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }
}
