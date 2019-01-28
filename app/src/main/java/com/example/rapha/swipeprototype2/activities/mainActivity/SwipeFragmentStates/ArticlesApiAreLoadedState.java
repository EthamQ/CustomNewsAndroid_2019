package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;

public class ArticlesApiAreLoadedState extends SwipeFragmentState implements ISwipeFragmentState {
    public ArticlesApiAreLoadedState(SwipeFragment swipeFragment) {
        super(swipeFragment);
    }

    @Override
    public void setCardsVisibility() {
        if(swipeFragment.apiArticlesToAdd.size() > 0){
            swipeFragment.setCardsVisibility(true);
            changeStateTo(new ApiArticlesAddedToViewState(swipeFragment));
        }
        else{
            changeStateTo(new ApiNotAvailableState(swipeFragment));
        }
    }

    @Override
    public void loadArticlesFromDb() {

    }

    @Override
    public void loadArticlesFromApi() {

    }

    @Override
    public void saveArticlesInDb() {
        if(swipeFragment != null){
            if(swipeFragment.getActivity() != null){
                NewsArticleDbService.getInstance(swipeFragment.getActivity().getApplication()).deleteAll();
                NewsArticleDbService.getInstance(swipeFragment.getActivity().getApplication())
                        .insertNewsArticles(swipeFragment.apiArticlesToAdd);
            }
        }
    }

    @Override
    public void addArticlesToView() {
        swipeFragment.addArticlesToView(swipeFragment.apiArticlesToAdd);
    }

    @Override
    public void articlesFromApiAreLoaded() {

    }

    @Override
    public void handleArticlesOnEmpty() {

    }

    @Override
    public void loadArticles() {

    }
}
