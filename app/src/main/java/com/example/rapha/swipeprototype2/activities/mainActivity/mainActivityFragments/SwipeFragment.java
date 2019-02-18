package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates.ISwipeFragmentState;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
import com.example.rapha.swipeprototype2.dataStorage.ArticleDataStorage;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.loading.DailyNewsLoadingService;
import com.example.rapha.swipeprototype2.loading.SwipeLoadingService;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.questionCards.QuestionCardService;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.LanguageCombinationDbService;
import com.example.rapha.swipeprototype2.roomDatabase.OffsetDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.DeleteData;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.IDeletesArticle;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.ErrorSwipeCard;
import com.example.rapha.swipeprototype2.swipeCardContent.ISwipeCard;
import com.example.rapha.swipeprototype2.swipeCardContent.IntroductionSwipeCard;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.RatingDbService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.sharedPreferencesAccess.SwipeTimeService;
import com.example.rapha.swipeprototype2.utils.Logging;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SwipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SwipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeFragment extends Fragment implements IKeyWordProvider, IDeletesArticle {

    public MainActivity mainActivity;
    public View view;

    // When this amount of articles is left in
    // swipeCardsList we load new articles from the api
    public static final int articlesAmountLoad = 10;

    // How many articles to load in the beginning from the db.
    public static final int articlesAmountLoadFromDb = ApiService.MAX_NUMBER_OF_ARTICLES;

    // Adapter for the fling Container (swipe functionality)
    public NewsArticleAdapter articlesArrayAdapter;

    // "swipeCardsList" is added to the "articlesArrayAdapter" and contains
    // the news articles to be displayed
    public ArrayList<ISwipeCard> swipeCardsList;

    // Temporary store newly loaded articles
    public LinkedList<NewsArticle> apiArticlesToAdd;
    public LinkedList<NewsArticle> dbArticlesToAdd;

    // Contains all the user preferences fetched from the database (news category and its rating).
    public List<UserPreferenceRoomModel> liveCategoryRatings;

    public String requestOffsetFinance;
    public String requestOffsetFood;
    public String requestOffsetPolitics;
    public String requestOffsetMovie;
    public String requestOffsetTechnology;

    public List<KeyWordRoomModel> livekeyWords;

    public RatingDbService dbService;
    public NewsArticleDbService newsArticleDbService;
    public KeyWordDbService keyWordDbService;

    public ISwipeFragmentState swipeFragmentState;

    public boolean shouldReloadFragment = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SwipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_swipe, container, false);
        init();
        setLanguageDialog();
        setSwipeFunctionality();
        dbService.getAllUserPreferences().observe(mainActivity, dbCategoryRatings -> liveCategoryRatings = dbCategoryRatings);
        keyWordDbService.getAllKeyWords().observe(mainActivity, keyWords -> livekeyWords = keyWords);

        if(ArticleDataStorage.temporaryArticlesExist()){
            swipeCardsList.addAll(ArticleDataStorage.getTemporaryStoredArticles());
            articlesArrayAdapter.notifyDataSetChanged();
        }
        else{
            loadFromDb();
        }

        DailyNewsLoadingService.getLoading().observe(getActivity(), loading ->{
            if(loading){
                handleLoading(false, true, DailyNewsLoadingService.LOAD_DAILY_NEWS);
            }
            else{
                handleLoading(true, true, DailyNewsLoadingService.LOAD_DAILY_NEWS);
            }
        });

        return view;
    }

    public void loadFromDb(){
            Log.d("newswipe", "loadFromDb()");
            final NewsArticleDbService newsArticleDbService = NewsArticleDbService.getInstance(getActivity().getApplication());
            newsArticleDbService.getAllUnreadSwipeArticles().observe(
                    getActivity(),
                    new Observer<List<NewsArticleRoomModel>>() {
                        @Override
                        public void onChanged(@Nullable List<NewsArticleRoomModel> articleModels) {
                            final Observer articleObserver = this;
                            Log.d("newswipe", "loadFromDb() onchanged");
                            Log.d("newswipe", "loadFromDb() onchanged article size: " + articleModels.size());
                            if(articleModels.isEmpty()){
                                loadFromApi();
                                newsArticleDbService.getAllUnreadSwipeArticles().removeObserver(this);
                            }
                            else{
                                swipeCardsList.addAll(newsArticleDbService.createNewsArticleList(articleModels, articleModels.size()));
                                keyWordDbService.getAllKeyWords().observe(mainActivity, new Observer<List<KeyWordRoomModel>>() {
                                    @Override
                                    public void onChanged(@Nullable List<KeyWordRoomModel> keyWords) {
                                            if(!keyWords.isEmpty()){
                                                QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeCardsList, livekeyWords);
                                                articlesArrayAdapter.notifyDataSetChanged();
                                                newsArticleDbService.getAllUnreadSwipeArticles().removeObserver(articleObserver);
                                                keyWordDbService.getAllKeyWords().removeObserver(this);
                                            }
                                    }
                                });


                            }
                        }
                    });
    }

    public void loadFromApi(){
        Log.d("newswipe", "loadFromApi()");
            Thread thread = new Thread(() -> {
                    try {
                        apiArticlesToAdd = new LinkedList<>();
                        apiArticlesToAdd = ApiService.getAllArticlesNewsApi(SwipeFragment.this, liveCategoryRatings);
                        Log.d("newswipe", "articles from api call: " + apiArticlesToAdd.size());
                        Log.d("newswipe", "first article from api call: " + apiArticlesToAdd.get(0).toString());
                        mainActivity.runOnUiThread(() -> {
                            storeArticlesInDatabase(apiArticlesToAdd);
                            // swipeCardsList.clear();
                            swipeCardsList.addAll(apiArticlesToAdd);
                            Logging.logSwipeCards(swipeCardsList, "newswipe2");
                            //checkIfDateProblem();
                            QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeCardsList, livekeyWords);

                            reloadFragment();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            });
            thread.start();
    }

    public void storeArticlesInDatabase(LinkedList<NewsArticle> articles){
        if(this != null){
            if(getActivity() != null){
                DeleteData deleteData = new DeleteData();
                deleteData.deletesArticle = this;
                deleteData.data = articles;
                NewsArticleDbService.getInstance(getActivity().getApplication())
                        .deleteAllSwipedArticles(deleteData);
            }
        }
    }

    @Override
    public void onDeleted(DeleteData deleteData) {
        if(!(getActivity() == null)){
            NewsArticleDbService.getInstance(getActivity().getApplication())
                    .insertNewsArticles((LinkedList<NewsArticle>) deleteData.data);
        }
    }

    /**
     * Sets the functionality for the flingContainer which handles the functionality
     * if the user swipes to the left or right etc.
     */
    public void setSwipeFunctionality(){
        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.frame);
        flingContainer.setAdapter(articlesArrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if(swipeCardsList.size() == 1){
                    swipeCardsList.add(new ErrorSwipeCard());
                }
                Log.d("newswipe", "++++++++++++++++++++++++");
                Log.d("newswipe", "article: " + swipeCardsList.get(0).toString());
                swipeCardsList.remove(0);
                Log.d("newswipe", "articles left: " + swipeCardsList.size());

                Log.d("newswipe", "++++++++++++++++++++++++");
                if(swipeCardsList.size() <= 5){
                    loadFromApi();
                }
                articlesArrayAdapter.notifyDataSetChanged();
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onLeftCardExit(Object dataObject) {
                final ISwipeCard swipedCard = (ISwipeCard)dataObject;
                swipedCard.dislike(SwipeFragment.this);
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onRightCardExit(Object dataObject) {
                final ISwipeCard swipedCard = (ISwipeCard)dataObject;
                swipedCard.like(SwipeFragment.this);
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
                ISwipeCard clickedArticle = (ISwipeCard)dataObject;
                clickedArticle.onClick(mainActivity);
            }
        });
    }

    @Override
    public List<KeyWordRoomModel> getCurrentKeyWords() {
        return livekeyWords;
    }

    public void init(){
        ((Toolbar) mainActivity.findViewById(R.id.toolbar)).setTitle("Swipe");
        swipeCardsList = new ArrayList<>();
        // Only add introduction card when the user just started the app.
        if(mainActivity.showIntroductionCard()){
            swipeCardsList.add(new IntroductionSwipeCard());
            mainActivity.introductionCardWasShown();
        }
        dbService = RatingDbService.getInstance(getActivity().getApplication());
        initAdapter();
        newsArticleDbService = NewsArticleDbService.getInstance(getActivity().getApplication());
        keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
        dbArticlesToAdd = new LinkedList<>();
        keepOffsetUpToDate(false);
    }


    public void initAdapter(){
        articlesArrayAdapter = new NewsArticleAdapter(getActivity(), R.layout.swipe_card, swipeCardsList);
        setSwipeFunctionality();
    }

    /**
     * Set the OnClickListener for the language button so it displays
     * an alert dialog that makes it possible for the user to select in which languages
     * he wants to see his news.
     */
    public void setLanguageDialog(){
        Button button = view.findViewById(R.id.button_languages);

        button.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new
                    AlertDialog.Builder(mainActivity);
            dialog.setTitle("Select languages");
            final String[] languageItems = LanguageSettingsService.languageItems;
            final boolean[] initialSelection = LanguageSettingsService.loadChecked(mainActivity);
            final boolean[] currentSelection = LanguageSettingsService.loadChecked(mainActivity);

            dialog.setMultiChoiceItems(languageItems, currentSelection, (dialogInterface, position, isChecked) ->   {
                currentSelection[position] = isChecked;
                LanguageSettingsService.saveChecked(mainActivity, currentSelection);
            });

            dialog.setPositiveButton("Confirm choice", (positiveDialog, which) -> {
                if(LanguageSettingsService.userChangedLanguage(initialSelection, currentSelection)){
                    keepOffsetUpToDate(true);
                }
                positiveDialog.cancel();
            });

            dialog.setNegativeButton("Cancel", (negativeDialog, which) -> {
                LanguageSettingsService.saveChecked(mainActivity, initialSelection);
                negativeDialog.cancel();
            });

            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        });
    }


    public void reloadFragment(){
        mainActivity.changeFragmentTo(R.id.nav_home);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        ArticleDataStorage.clearData();
        ArticleDataStorage.storeArticlesTemporarily(this.swipeCardsList);
    }

    public void keepOffsetUpToDate(boolean calledByChangeLanguage){
        OffsetDbService offsetDbService = OffsetDbService.getInstance(getActivity().getApplication());
//        offsetDbService.getAll().observe(getActivity(), entries ->{
//            for(int i = 0; i < entries.size(); i++){
//                Log.d("offsetss", entries.get(i).toString());
//            }
//        });

        LanguageCombinationDbService comboDbService = LanguageCombinationDbService.getInstance(getActivity().getApplication());
//        comboDbService.getAll().observe(getActivity(), entries ->{
//            for(int i = 0; i < entries.size(); i++){
//                Log.d("offsetss", entries.get(i).toString());
//            }
//        });

        comboDbService.getAll().observe(getActivity(), combinations ->{
            boolean[] currentLanguages = LanguageSettingsService.loadChecked(mainActivity);
            this.requestOffsetFood = "";
            this.requestOffsetMovie = "";
            this.requestOffsetFinance = "";
            this.requestOffsetPolitics = "";
            this.requestOffsetTechnology = "";
            for(int i = 0; i < combinations.size(); i++){
                final LanguageCombinationRoomModel currentCombination = combinations.get(i);
                boolean combinationExists = currentCombination.german == currentLanguages[LanguageSettingsService.INDEX_GERMAN]
                        && currentCombination.french == currentLanguages[LanguageSettingsService.INDEX_FRENCH]
                        && currentCombination.russian == currentLanguages[LanguageSettingsService.INDEX_RUSSIAN]
                        && currentCombination.english == currentLanguages[LanguageSettingsService.INDEX_ENGLISH];
                if(combinationExists && !(getActivity() == null)){
                    offsetDbService.getAll().observe(getActivity(), offsets ->{
                        for(int j = 0; j < offsets.size(); j++){
                            if(offsets.get(j).languageCombination == currentCombination.id){
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Movie.CATEGORY_ID){
                                    this.requestOffsetMovie = offsets.get(j).requestOffset;
                                    //Log.d("newswipe", "Swipe fragment set movies offset to: " + this.requestOffsetMovie);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Finance.CATEGORY_ID){
                                    this.requestOffsetFinance = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set finance offset to: " + this.requestOffsetFinance);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Technology.CATEGORY_ID){
                                    this.requestOffsetTechnology = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set technolgy offset to: " + this.requestOffsetTechnology);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Politics.CATEGORY_ID){
                                    this.requestOffsetPolitics = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set politic offset to: " + this.requestOffsetPolitics);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Food.CATEGORY_ID){
                                    this.requestOffsetFood = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set food offset to: " + this.requestOffsetFood);
                                }
                            }
                        }
                        Log.d("ofsss", "============================");
                    });
                }
                if(calledByChangeLanguage && (i == combinations.size() - 1)){
                    swipeCardsList.clear();
                    shouldReloadFragment = true;
                    loadFromApi();
                }
            }
        });
    }

    public void checkIfDateProblem(){
        String newest = this.requestOffsetFood;
        DateTime newestDate = new DateTime( newest );
        int id = 4;
        for(int i = 0; i < swipeCardsList.size(); i++){
            ISwipeCard current = swipeCardsList.get(i);
            if(current instanceof NewsArticle){
                if(((NewsArticle)current).newsCategory == id){
                    DateTime published = new DateTime( ((NewsArticle)current).publishedAt );
                    Log.d("checkdate", "compare: " + published + ", to: " + newestDate);
                    if(published.isBefore(newestDate)){
                        Log.d("checkdate", "published is older than newest allowed");
                    }
                    else if(published.isAfter(newestDate)){
                        Log.d("checkdate", "PROBLEM: published is newer than newest allowed");
                        newestDate = newestDate.minusDays(1);
                        int hours = newestDate.getHourOfDay();
                        int delta = 23 - hours;
                        newestDate = newestDate.plusHours(delta);
                        this.requestOffsetFood = newestDate.toString();
                        swipeCardsList.clear();
                        loadFromApi();
                        break;
                    }
                }
            }
        }
    }































































































































    public void handleLoading(boolean visible, boolean changeLoadingGif, int loadingType){
        int visibility = visible ? View.VISIBLE : View.INVISIBLE;
        view.findViewById(R.id.frame).setVisibility(visibility);
        view.findViewById(R.id.button_languages).setVisibility(visibility);
        if(changeLoadingGif){
            int visibilityLoading = visible ? GifImageView.INVISIBLE : GifImageView.VISIBLE;
            GifImageView loading = view.findViewById(R.id.loading);
            loading.setVisibility(visibilityLoading);
            TextView loadingInfo = view.findViewById(R.id.loading_info);
            loadingInfo.setVisibility(visibilityLoading);
            String loadingText = "Loading articles, please wait a moment...";
            switch(loadingType){
                case SwipeLoadingService.CHANGE_LANGUAGE:
                    loadingText = "Changing language..."; break;
                case DailyNewsLoadingService.LOAD_DAILY_NEWS:
                     loadingText = "Loading your daily news..."; break;

            }
            loadingInfo.setText(loadingText);
        }

    }



    private void debug(){
        OffsetDbService offsetDbService = OffsetDbService.getInstance(getActivity().getApplication());
        offsetDbService.getAll().observe(getActivity(), entries ->{
            for(int i = 0; i < entries.size(); i++){
                Log.d("offsetss", entries.get(i).toString());
            }
        });

        LanguageCombinationDbService comboDbService = LanguageCombinationDbService.getInstance(getActivity().getApplication());
        comboDbService.getAll().observe(getActivity(), entries ->{
            for(int i = 0; i < entries.size(); i++){
                Log.d("offsetss", entries.get(i).toString());
            }
        });


        comboDbService.getAll().observe(getActivity(), combinations ->{
            boolean[] currentLanguages = LanguageSettingsService.loadChecked(mainActivity);
            this.requestOffsetFood = "";
            this.requestOffsetMovie = "";
            this.requestOffsetFinance = "";
            this.requestOffsetPolitics = "";
            this.requestOffsetTechnology = "";
            for(int i = 0; i < combinations.size(); i++){
                final LanguageCombinationRoomModel currentCombination = combinations.get(i);
                boolean combinationExists = currentCombination.german == currentLanguages[LanguageSettingsService.INDEX_GERMAN]
                        && currentCombination.french == currentLanguages[LanguageSettingsService.INDEX_FRENCH]
                        && currentCombination.russian == currentLanguages[LanguageSettingsService.INDEX_RUSSIAN]
                        && currentCombination.english == currentLanguages[LanguageSettingsService.INDEX_ENGLISH];
                if(combinationExists){
                    offsetDbService.getAll().observe(getActivity(), offsets ->{
                        for(int j = 0; j < offsets.size(); j++){
                            if(offsets.get(j).languageCombination == currentCombination.id){
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Movie.CATEGORY_ID){
                                    this.requestOffsetMovie = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set movies offset to: " + this.requestOffsetMovie);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Finance.CATEGORY_ID){
                                    this.requestOffsetFinance = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set finance offset to: " + this.requestOffsetFinance);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Technology.CATEGORY_ID){
                                    this.requestOffsetTechnology = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set technolgy offset to: " + this.requestOffsetTechnology);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Politics.CATEGORY_ID){
                                    this.requestOffsetPolitics = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set politic offset to: " + this.requestOffsetPolitics);
                                }
                                if(offsets.get(j).categoryId == NewsCategoryContainer.Food.CATEGORY_ID){
                                    this.requestOffsetFood = offsets.get(j).requestOffset;
                                    Log.d("ofsss", "Swipe fragment set food offset to: " + this.requestOffsetFood);
                                }
                            }
                        }
                        Log.d("ofsss", "============================");
                    });
                }
            }
        });
    }



    public void getKeyWordsFromDb(){
        keyWordDbService.getAllKeyWords().observe(mainActivity, keyWords -> {
                livekeyWords = keyWords;
                Logging.logKeyWordsFromDb(keyWords);
        });
    }



    /**
     * Calls the ApiService to receive all news articles and adds them to "swipeCardsList"
     * which shows them on the cards to the user.
     */
    public void loadArticlesFromApi(){
        if(shouldRequestArticles()){
            SwipeTimeService.saveLastLoaded(mainActivity, new Date());
            Log.d("LOADD", "loadArticlesFromApi()");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Clean previous data if it exists.
                        apiArticlesToAdd = new LinkedList<>();
                        // Load articles.
                        apiArticlesToAdd = ApiService.getAllArticlesNewsApi(SwipeFragment.this, liveCategoryRatings);
                        storeArticlesInDatabase(apiArticlesToAdd);
                        Log.d("AMOUNT", "news articles loaded: " + apiArticlesToAdd.size());
                        mainActivity.runOnUiThread(() -> {
                                // Tell the state that data is ready
                                swipeFragmentState.articlesFromApiAreLoaded();
                                // Store them to have cached data
                                // when the user opens the application the next time.
                                swipeFragmentState.saveArticlesInDb();
                                // swipeFragmentState.addArticlesToView();

                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("HTTPERROR", e.toString());
                    }
                }
            });
            thread.start();
        }
    }


    /**
     * Adds news articles from the list "apiArticlesToAdd" to the "swipeCardsList"
     * which is displayed on the cards in the view.
     */
    public void addArticlesToView(LinkedList<NewsArticle> articlesToAdd) {
        swipeCardsList.addAll(articlesToAdd);
        swipeFragmentState.setCardsVisibility();
        swipeFragmentState.handleAfterAddedToView();
        articlesArrayAdapter.notifyDataSetChanged();
        Logging.logAmountOfArticles(mainActivity);
        Logging.logSwipeCards(swipeCardsList, "addedtoview");
        if(shouldReloadFragment){
            reloadFragment();
            shouldReloadFragment = false;
        }
    }

    public boolean shouldRequestArticles(){
        return swipeCardsList.size() <= articlesAmountLoad;
    }

    public boolean apiArticlesHaveBeenLoaded(){
        return apiArticlesToAdd.size() > 0;
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static SwipeFragment newInstance(String param1, String param2) {
        SwipeFragment fragment = new SwipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.mainActivity = (MainActivity) getActivity();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
