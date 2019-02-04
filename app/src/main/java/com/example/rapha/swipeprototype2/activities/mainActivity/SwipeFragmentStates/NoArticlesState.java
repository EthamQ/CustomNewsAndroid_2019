package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.dataStorage.ArticleDataStorage;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import java.util.List;

public class NoArticlesState extends SwipeFragmentState implements ISwipeFragmentState {
    public NoArticlesState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "NoArticlesState");
    }

    @Override
    public void loadArticles() {
        if(ArticleDataStorage.temporaryArticlesExist()){
            loadTemporarilyStoredCards();
        }
        else{
            loadDatabaseCards();
        }
    }

    private void loadTemporarilyStoredCards(){
        swipeFragment.articlesArrayList.addAll(ArticleDataStorage.getTemporaryStoredArticles());
        swipeFragment.setCardsVisibility(true);
        changeStateTo(new LoadArticlesFromApiState(swipeFragment));
    }

    private void loadDatabaseCards(){
        final NewsArticleDbService newsArticleDbService = NewsArticleDbService.getInstance(swipeFragment.getActivity().getApplication());
        newsArticleDbService.getAllArticles().observe(
                swipeFragment.getActivity(),
                new Observer<List<NewsArticleRoomModel>>() {
                    @Override
                    public void onChanged(@Nullable List<NewsArticleRoomModel> articleModels) {
                            swipeFragment.dbArticlesToAdd.addAll(
                                    newsArticleDbService.createNewsArticleList(
                                            articleModels,
                                            swipeFragment.articlesAmountLoadFromDb
                                    )
                            );
                            changeStateTo(new AddDbArticlesToViewState(swipeFragment));
                            // Call it here to make sure db data already loaded.
                            swipeFragment.swipeFragmentState.addArticlesToView();
                        // Articles only have to be loaded once in the beginning.
                        newsArticleDbService.getAllArticles().removeObserver(this);
                    }
                });
    }

    @Override
    public void setCardsVisibility() {
        swipeFragment.setCardsVisibility(false);
    }

    @Override
    public void loadArticlesFromDb() {


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
