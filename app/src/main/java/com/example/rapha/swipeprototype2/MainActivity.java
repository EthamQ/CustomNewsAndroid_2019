package com.example.rapha.swipeprototype2;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapha.swipeprototype2.UserPreferences.PreferenceRatingService;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
import com.example.rapha.swipeprototype2.roomDatabase.DbService;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<NewsArticle> articlesArrayList;
    private NewsArticleAdapter articlesArrayAdapter;
    private LinkedList<NewsArticle> newsArticlesNewsApi;
    private DbService dbService;
    List<UserPreferenceRoomModel> liveUserPreferences;
    // TODO: Use state pattern
    boolean articlesShouldBeLoaded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        setSwipeFunctionality();

        dbService.getAllUserPreferences().observe(this, new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> userPreferenceRoomModels) {
                liveUserPreferences = userPreferenceRoomModels;
                for(int i = 0; i< userPreferenceRoomModels.size(); i++){
                    Log.d("FROMDB", "Rating: " + userPreferenceRoomModels.get(i));
                    //ur.deleteAll();
                    if(articlesShouldBeLoaded){
                        loadArticles(userPreferenceRoomModels);
                        articlesShouldBeLoaded = false;
                    }
                }
            }
        });
    }

    /**
     * Initialize global objects.
     */
    public void init(){
        articlesArrayList = new ArrayList<>();
        // Add empty article to show while real articles are being requested from the api.
        // TODO: wait until real articles are loaded
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
                            // Add articles to articlesArrayList.
                            articlesArrayList.addAll(newsArticlesNewsApi);
                            TextView tv = findViewById(R.id.helloText);
                            tv.setText("Articles loaded, start to swipe");
                            articlesArrayAdapter.notifyDataSetChanged();

                            // ugly quick testing
                            int amount0 = 0;
                            int amount1 = 0;
                            int amount2 = 0;
                            int amount3 = 0;
                            int amount4 = 0;
                            for(int i = 0; i< newsArticlesNewsApi.size(); i++){
                                Log.d("§§§", newsArticlesNewsApi.get(i).toString());
                                if(newsArticlesNewsApi.get(i).newsCategory == 0){
                                    amount0++;
                                }
                                if(newsArticlesNewsApi.get(i).newsCategory == 1){
                                    amount1++;
                                }
                                if(newsArticlesNewsApi.get(i).newsCategory == 2){
                                    amount2++;
                                }
                                if(newsArticlesNewsApi.get(i).newsCategory == 3){
                                    amount3++;
                                }
                                if(newsArticlesNewsApi.get(i).newsCategory == 4){
                                    amount4++;
                                }
                            }
                            Log.d("&&&", "0: " + amount0 + "\n"
                                    + "1: " + amount1 + "\n"
                                    + "2: " + amount2 + "\n"
                                    + "3: " + amount3 + "\n"
                                    + "4: " + amount4 + "\n");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
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

            @Override
            public void onLeftCardExit(Object dataObject) {
                NewsArticle swipedArticle = (NewsArticle)dataObject;
                PreferenceRatingService.rateAsNotInteresting(getApplication(), MainActivity.this, swipedArticle);
            }

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

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MainActivity.this, "Clicked!");
                Intent intent = new Intent(MainActivity.this, ArticleDetailScrollingActivity.class);
                intent.putExtra("clickedArticle", (NewsArticle)dataObject);
                startActivity(intent);
            }
        });
    }
}
