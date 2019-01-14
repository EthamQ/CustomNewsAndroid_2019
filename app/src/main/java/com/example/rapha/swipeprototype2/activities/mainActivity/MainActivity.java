package com.example.rapha.swipeprototype2.activities.mainActivity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.Logging;
import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.ArticleDetailScrollingActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates.ArticlesNotLoadedState;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates.IMainActivityState;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.categoryDistribution.CategoryRatingService;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
import com.example.rapha.swipeprototype2.roomDatabase.DbService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Everything public so the state classes can access them

    // When "articlesAmountReload" articles are left in
    // articlesArrayList we load new articles from the api
    public static final int articlesAmountReload = 10;

    // Adapter for the fling Container (swipe functionality)
    public NewsArticleAdapter articlesArrayAdapter;

    // "articlesArrayList" is added to the "articlesArrayAdapter" and contains
    // the news articles to be displayed
    public ArrayList<NewsArticle> articlesArrayList;

    // Temporary store newly loaded articles in "newsArticlesToSwipe"
    // before they are added to "articlesArrayList"
    public LinkedList<NewsArticle> newsArticlesToSwipe;

    // Contains all the user preferences fetched from the database (news category and its rating).
    public List<UserPreferenceRoomModel> liveUserPreferences;

    public DbService dbService;
    public IMainActivityState mainActivityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityState = new ArticlesNotLoadedState(this);
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
                    // Clean previous data if it exists.
                    newsArticlesToSwipe = new LinkedList<>();
                    // Load articles.
                    newsArticlesToSwipe = ApiService.getAllArticlesNewsApi(userPreferenceRoomModels);
                    Log.d("AMOUNT", "news articles loaded: " + newsArticlesToSwipe.size());
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
     * Adds news articles from the list "newsArticlesToSwipe" to the "articlesArrayList"
     * which is displayed on the cards in the view.
     */
    public void addArticlesToView() {
        articlesArrayList.addAll(newsArticlesToSwipe);
        articlesArrayAdapter.notifyDataSetChanged();

        // Pseudo functionality to show when the articles are loaded.
        TextView textView = findViewById(R.id.itemText);
        textView.setText("Articles loaded, start to swipe");
        Logging.logAmountOfArticles(this);
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
                // Handled here because every swiped card is removed here.
                mainActivityState.handleArticlesOnEmpty();
                articlesArrayAdapter.notifyDataSetChanged();
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onLeftCardExit(Object dataObject) {
                NewsArticle swipedArticle = (NewsArticle)dataObject;
                CategoryRatingService.rateAsNotInteresting(getApplication(), MainActivity.this, swipedArticle);
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onRightCardExit(Object dataObject) {
                final NewsArticle swipedArticle = (NewsArticle)dataObject;
                CategoryRatingService.rateAsInteresting(getApplication(), MainActivity.this, swipedArticle);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // didn't always work so implemented elsewhere
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
