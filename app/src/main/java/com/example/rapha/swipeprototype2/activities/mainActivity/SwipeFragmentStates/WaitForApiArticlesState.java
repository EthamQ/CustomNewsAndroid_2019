package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;
import android.util.Log;
import android.widget.Button;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class WaitForApiArticlesState extends SwipeFragmentState implements ISwipeFragmentState {
    public WaitForApiArticlesState(SwipeFragment swipeFragment) {
        super(swipeFragment);
        Log.d("statehistory", "WaitForApiArticlesState");
    }

    @Override
    public void articlesFromApiAreLoaded() {
        Button loading = swipeFragment.view.findViewById(R.id.loading);
        loading.setVisibility(Button.INVISIBLE);
        changeStateTo(new ArticlesApiAreLoadedState(swipeFragment));
    }

    @Override
    public void handleArticlesOnEmpty() { }
    @Override
    public void loadArticles() { }
    @Override
    public void setCardsVisibility() { }
    @Override
    public void handleAfterAddedToView() { }
    @Override
    public void loadArticlesFromApi() { }
    @Override
    public void saveArticlesInDb() { }
    @Override
    public void addArticlesToView() { }
}
