//
//
//
//package com.example.rapha.swipeprototype2.activities;
//
//        import android.arch.lifecycle.Observer;
//
//        import android.content.DialogInterface;
//        import android.content.Intent;
//        import android.net.Uri;
//        import android.support.annotation.NonNull;
//        import android.support.annotation.Nullable;
//        import android.support.design.widget.NavigationView;
//        import android.support.v4.app.FragmentManager;
//        import android.support.v4.view.GravityCompat;
//        import android.support.v4.widget.DrawerLayout;
//        import android.support.v7.app.ActionBarDrawerToggle;
//        import android.support.v7.app.AlertDialog;
//        import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.MenuItem;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.LinearLayout;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import com.example.rapha.swipeprototype2.activities.StatisticsActivity;
//        import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
//        import com.example.rapha.swipeprototype2.languageSettings.LanguageSettingsService;
//        import com.example.rapha.swipeprototype2.utils.Logging;
//        import com.example.rapha.swipeprototype2.R;
//        import com.example.rapha.swipeprototype2.activities.articleDetailActivity.ArticleDetailScrollingActivity;
//        import com.example.rapha.swipeprototype2.activities.swipeFragment.mainActivityStates.ArticlesNotLoadedState;
//        import com.example.rapha.swipeprototype2.activities.swipeFragment.mainActivityStates.IMainActivityState;
//        import com.example.rapha.swipeprototype2.models.NewsArticle;
//        import com.example.rapha.swipeprototype2.categoryDistribution.CategoryRatingService;
//        import com.example.rapha.swipeprototype2.api.ApiService;
//        import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
//        import com.example.rapha.swipeprototype2.roomDatabase.DbService;
//        import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;
//        import com.lorentzos.flingswipe.SwipeFlingAdapterView;
//
//        import java.util.ArrayList;
//        import java.util.LinkedList;
//        import java.util.List;
//
//public class Main2Activity extends AppCompatActivity {
//
////    // Navigation drawer
////    private DrawerLayout drawerLayout;
////    private ActionBarDrawerToggle actionBarDrawerToggle;
////    private NavigationView navigationView;
////
////    // When "articlesAmountReload" articles are left in
////    // articlesArrayList we load new articles from the api
////    public static final int articlesAmountReload = 10;
////
////    // Adapter for the fling Container (swipe functionality)
////    public NewsArticleAdapter articlesArrayAdapter;
////
////    // "articlesArrayList" is added to the "articlesArrayAdapter" and contains
////    // the news articles to be displayed
////    public ArrayList<NewsArticle> articlesArrayList;
////
////    // Temporary store newly loaded articles in "apiArticlesToAdd"
////    // before they are added to "articlesArrayList"
////    public LinkedList<NewsArticle> apiArticlesToAdd;
////
////    // Contains all the user preferences fetched from the database (news category and its rating).
////    public List<UserPreferenceRoomModel> liveCategoryRatings;
////
////    public DbService dbService;
////    public IMainActivityState swipeActivityState;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        swipeActivityState = new ArticlesNotLoadedState(this);
////        init();
////        initNavigationDrawer();
////        setSwipeFunctionality();
////        setLanguageDialog();
////        setTitle("Home");
////
////        // Set visible when article data is here
////        findViewById(R.id.frame).setVisibility(View.INVISIBLE);
////        findViewById(R.id.button_languages).setVisibility(View.INVISIBLE);
////
////        // Fetch all user preferences from the api and use them to load
////        // new articles from the api. We need the old preferences to decide
////        // how many news we want to load for each news category.
////        dbService.getAllUserPreferences().observe(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this, new Observer<List<UserPreferenceRoomModel>>() {
////            @Override
////            public void onChanged(@Nullable List<UserPreferenceRoomModel> userPreferenceRoomModels) {
////                liveCategoryRatings = userPreferenceRoomModels;
////                for(int i = 0; i < userPreferenceRoomModels.size(); i++){
////                    Log.d("RATINGLIVE", userPreferenceRoomModels.get(i).toString());
////                }
////                swipeActivityState.loadArticlesFromApi(userPreferenceRoomModels);
////            }
////        });
////
//////        FragmentManager fragmentManager = getSupportFragmentManager();
//////        MainActivity swipeFragment = new MainActivity();
//////        fragmentManager.beginTransaction()
//////                .add(R.id.frame, swipeFragment)
//////                .addToBackStack(null)
//////                .commit();
////    }
////
////    /**
////     * Initialize global objects.
////     */
////    public void init(){
////        articlesArrayList = new ArrayList<>();
////        // Add empty article to show while real articles are being requested from the api.
////        // TODO: wait until real articles are loaded / use caching
////        NewsArticle firstCard = new NewsArticle();
////        firstCard.isDefault = true;
////        articlesArrayList.add(firstCard);
////        articlesArrayAdapter = new NewsArticleAdapter(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this, R.layout.swipe_card, articlesArrayList);
////        dbService = DbService.getInstance(getApplication());
////    }
////
////    public void initNavigationDrawer(){
////        drawerLayout = findViewById(R.id.activity_main);
////        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);
////
////        drawerLayout.addDrawerListener(actionBarDrawerToggle);
////        actionBarDrawerToggle.syncState();
////
////
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////
////        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
////        navigationView.setNavigationItemSelectedListener(this);
////
////
////    }
////
////
////    @Override
////    public boolean onNavigationItemSelected(@NonNull MenuItem swipe_card) {
////        Log.d("MENUU", "first: ");
//////        if(swipe_card.getItemId() == R.id.mycart){
//////            Intent inten = new Intent(MainActivity.this, StatisticsActivity.class);
//////            startActivity(inten);
//////        }
////        // Handle navigation view swipe_card clicks here.
////
//////        switch (swipe_card.getItemId()) {
//////
//////            case R.id.nav_maths: {
//////                //do somthing
//////                break;
//////            }
//////        }
////        //close navigation drawer
////        drawerLayout.closeDrawer(GravityCompat.START);
////        return true;
////    }
////
////    /**
////     * Set the OnClickListener for the language button so it displays
////     * an alert dialog that makes it possible for the user to select in which languages
////     * he wants to see his news.
////     */
////    public void setLanguageDialog(){
////        Button button = findViewById(R.id.button_languages);
////        button.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                AlertDialog.Builder dialog = new
////                        AlertDialog.Builder(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this);
////                dialog.setTitle("Select languages");
////                final String[] languageItems = LanguageSettingsService.languageItems;
////                final boolean[] checkedItems = LanguageSettingsService.loadChecked(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this);
////                dialog.setMultiChoiceItems(languageItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener()  {
////                    @Override
////                    public void onClick(DialogInterface var1, int which, boolean isChecked){
////                        checkedItems[which] = isChecked;
////                        LanguageSettingsService.saveChecked(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this, checkedItems);
////                    }
////                });
////                dialog.setPositiveButton("Confirm choice", new
////                        DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.cancel();
////                            }
////                        });
////                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int id) {
////                        dialog.cancel();
////                    }
////                });
////                AlertDialog alertDialog = dialog.create();
////                alertDialog.show();
////            }
////        });
////    }
////
////    /**
////     * Calls the ApiService to receive all news articles and adds them to "articlesArrayList"
////     * which shows them on the cards to the user.
////     */
////    public void loadArticlesFromApi(final List<UserPreferenceRoomModel> userPreferenceRoomModels){
////        Thread thread = new Thread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    // Clean previous data if it exists.
////                    apiArticlesToAdd = new LinkedList<>();
////                    // Load articles.
////                    apiArticlesToAdd = ApiService.getAllArticlesNewsApi(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this, userPreferenceRoomModels);
////                    // TODO: don'actionBarDrawerToggle load all images at once, the application can'actionBarDrawerToggle handle it!
////                    // ArticleImageService.setImagesForTextView(apiArticlesToAdd, 0);
////                    Log.d("AMOUNT", "news articles loaded: " + apiArticlesToAdd.size());
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            swipeActivityState.articlesAreLoaded();
////                            swipeActivityState.addArticlesToView();
////                            findViewById(R.id.frame).setVisibility(View.VISIBLE);
////                            findViewById(R.id.button_languages).setVisibility(View.VISIBLE);
////                        }
////                    });
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    Log.e("HTTPERROR", e.toString());
////                }
////            }
////        });
////        thread.start();
////    }
////
////    /**
////     * Adds news articles from the list "apiArticlesToAdd" to the "articlesArrayList"
////     * which is displayed on the cards in the view.
////     */
////    public void addArticlesToView() {
////        articlesArrayList.addAll(apiArticlesToAdd);
////        articlesArrayAdapter.notifyDataSetChanged();
////
////        // Pseudo functionality to show when the articles are loaded.
////        TextView textView = findViewById(R.id.itemText);
////        textView.setText("Start swiping to read articles!\n\n " +
////                "Swipe interesting articles to the right\n\n " +
////                "Swipe articles that aren'actionBarDrawerToggle interesting to the left");
////        Logging.logAmountOfArticles(this);
////    }
////
////    /**
////     * Sets the functionality for the flingContainer which handles the functionality
////     * if the user swipes to the left or right etc.
////     */
////    public void setSwipeFunctionality(){
////        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
////        flingContainer.setAdapter(articlesArrayAdapter);
////        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
////            @Override
////            public void removeFirstObjectInAdapter() {
////                articlesArrayList.remove(0);
////                // Handled here because every swiped card is removed here.
////                swipeActivityState.handleArticlesOnEmpty();
////                articlesArrayAdapter.notifyDataSetChanged();
////            }
////
////            /**
////             * Gives the swiped news card a minus rating in the database.
////             * @param dataObject The swiped news card.
////             */
////            @Override
////            public void onLeftCardExit(Object dataObject) {
////                NewsArticle swipedArticle = (NewsArticle)dataObject;
////                CategoryRatingService.rateAsNotInteresting(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this.liveCategoryRatings, com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this, swipedArticle);
////            }
////
////            /**
////             * Gives the swiped news card a minus rating in the database.
////             * @param dataObject The swiped news card.
////             */
////            @Override
////            public void onRightCardExit(Object dataObject) {
////                final NewsArticle swipedArticle = (NewsArticle)dataObject;
////                CategoryRatingService.rateAsInteresting(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this.liveCategoryRatings, com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this, swipedArticle);
////            }
////
////            @Override
////            public void onAdapterAboutToEmpty(int itemsInAdapter) {
////                // didn'actionBarDrawerToggle always work so implemented elsewhere
////            }
////
////            @Override
////            public void onScroll(float scrollProgressPercent) {
//////                View view = flingContainer.getSelectedView();
//////                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//////                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
////            }
////        });
////
////        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
////            /**
////             * Opens the ArticleDetailScrollActivity which displays the clicked article.
////             * @param itemPosition
////             * @param dataObject
////             */
////            @Override
////            public void onItemClicked(int itemPosition, Object dataObject) {
////                Intent intent = new Intent(com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity.this, ArticleDetailScrollingActivity.class);
////                intent.putExtra("clickedArticle", (NewsArticle)dataObject);
////                startActivity(intent);
////            }
////        });
////    }
////
////    @Override
////    public void onFragmentInteraction(Uri uri) {
////
////    }
//}
//
