package com.example.rapha.swipeprototype2.activities.mainActivity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.ArticleDetailScrollingActivity;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.userPreferences.PreferenceRatingService;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
import com.example.rapha.swipeprototype2.roomDatabase.DbService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ArrayList<NewsArticle> articlesArrayList;
    public NewsArticleAdapter articlesArrayAdapter;
    public LinkedList<NewsArticle> newsArticlesNewsApi;
    public DbService dbService;
    List<UserPreferenceRoomModel> liveUserPreferences;
    public IMainActivityState mainActivityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityState = new LoadArticlesOnCreateState(this);
        init();
        setSwipeFunctionality();

        // Fetch all user preferences from the api and use them to load
        // new articles from the api. We need the old preferences to decide
        // how many news we want to load for each news category.
        dbService.getAllUserPreferences().observe(this, new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> userPreferenceRoomModels) {
                liveUserPreferences = userPreferenceRoomModels;
                mainActivityState.loadArticlesFromApi(userPreferenceRoomModels);
            }
        });
    }

    /**
     * Initialize global objects.
     */
    public void init(){
        articlesArrayList = new ArrayList<>();
        // Add empty article to show while real articles are being requested from the api.
        // TODO: wait until real articles are loaded / use caching
        articlesArrayList.add(new NewsArticle());
        articlesArrayAdapter = new NewsArticleAdapter(MainActivity.this, R.layout.item, articlesArrayList);
        dbService = DbService.getInstance(getApplication());
    }

    /**
     * Calls the ApiService to receive all news articles and adds them to "articlesArrayList"
     * which shows them on the cards to the user.
     */
    public void loadArticles(final List<UserPreferenceRoomModel> userPreferenceRoomModels){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Load articles.
                    newsArticlesNewsApi = ApiService.getAllArticlesNewsApi(userPreferenceRoomModels);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivityState.articlesAreLoaded();
                            mainActivityState.addArticlesToView();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    /**
     * Sets the functionality for the flingContainer which handles the functionality
     * if the user swipes to the left or right etc.
     */
    public void setSwipeFunctionality(){
        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(articlesArrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                articlesArrayList.remove(0);
                articlesArrayAdapter.notifyDataSetChanged();
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onLeftCardExit(Object dataObject) {
                NewsArticle swipedArticle = (NewsArticle)dataObject;
                PreferenceRatingService.rateAsNotInteresting(getApplication(), MainActivity.this, swipedArticle);
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onRightCardExit(Object dataObject) {
                final NewsArticle swipedArticle = (NewsArticle)dataObject;
                PreferenceRatingService.rateAsInteresting(getApplication(), MainActivity.this, swipedArticle);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //articlesArrayList.add("XML ".concat(String.valueOf(i)));
                //arrayAdapter.notifyDataSetChanged();
                //Log.d("LIST", "notified");
                //i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            /**
             * Opens the ArticleDetailScrollActivity which displays the clicked article.
             * @param itemPosition
             * @param dataObject
             */
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Intent intent = new Intent(MainActivity.this, ArticleDetailScrollingActivity.class);
                intent.putExtra("clickedArticle", (NewsArticle)dataObject);
                startActivity(intent);
            }
        });
    }
}
