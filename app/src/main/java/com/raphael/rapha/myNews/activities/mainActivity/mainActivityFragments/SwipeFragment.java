package com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.api.SwipeApiService;
import com.raphael.rapha.myNews.api.apiQuery.IQueryListener;
import com.raphael.rapha.myNews.customAdapters.NewsArticleAdapter;
import com.raphael.rapha.myNews.http.HttpRequestInfo;
import com.raphael.rapha.myNews.http.IHttpRequester;
import com.raphael.rapha.myNews.internetConnection.InternetConnectionService;
import com.raphael.rapha.myNews.languages.LanguageCombinationService;
import com.raphael.rapha.myNews.requestDateOffset.DateOffsetService;
import com.raphael.rapha.myNews.roomDatabase.NewsHistoryDbService;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRoomModel;
import com.raphael.rapha.myNews.sharedPreferencesAccess.SwipeTimeService;
import com.raphael.rapha.myNews.temporaryDataStorage.ArticleDataStorage;
import com.raphael.rapha.myNews.temporaryDataStorage.DateOffsetDataStorage;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.loading.DailyNewsLoadingService;
import com.raphael.rapha.myNews.loading.LoadingService;
import com.raphael.rapha.myNews.loading.SwipeLoadingService;
import com.raphael.rapha.myNews.questionCards.QuestionCardService;
import com.raphael.rapha.myNews.roomDatabase.KeyWordDbService;
import com.raphael.rapha.myNews.roomDatabase.LanguageCombinationDbService;
import com.raphael.rapha.myNews.roomDatabase.OffsetDbService;
import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.DeleteData;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.IDeletesArticle;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.ErrorSwipeCard;
import com.raphael.rapha.myNews.swipeCardContent.ISwipeCard;
import com.raphael.rapha.myNews.swipeCardContent.IntroductionSwipeCard;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.roomDatabase.NewsArticleDbService;
import com.raphael.rapha.myNews.roomDatabase.RatingDbService;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.raphael.rapha.myNews.temporaryDataStorage.LanguageSelectionDataStorage;
import com.raphael.rapha.myNews.utils.CollectionService;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
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
public class SwipeFragment extends Fragment implements IDeletesArticle, IQueryListener, IHttpRequester {

    public MainActivity mainActivity;
    public View view;

    // When this amount of swipe cards is left we load new articles from the api.
    public static final int ARTICLE_MINIMUM_BEFORE_LOADING = 10;

    // Adapter for the swipe cards plug in.
    public NewsArticleAdapter swipeCardArrayAdapter;

    // ArrayList for the swipe card adapter.
    public ArrayList<ISwipeCard> swipeCardsList;

    // Up to date data from observables to pass to other functions.
    public List<UserPreferenceRoomModel> liveCategoryRatings;
    public List<KeyWordRoomModel> liveKeyWords;

    // Database services.
    public RatingDbService ratingDbService;
    public NewsArticleDbService newsArticleDbService;
    public KeyWordDbService keyWordDbService;
    public OffsetDbService dateOffsetDbService;
    public LanguageCombinationDbService languageComboDbService;
    public NewsHistoryDbService newsHistoryDbService;

    // Only shown while loading
    Button abortLoading;
    public Button skip;

    // Booleans tracking the state.
    public boolean apiIsLoading = false;
    public boolean languageChangeIsLoading = false;
    boolean dbIsLoading = false;
    boolean canShowTooManyRequestDialogue = true;


    // Text to display when swiping left or right.
    public TextView leftIndicator;
    public TextView rightIndicator;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_swipe, container, false);

        initObjectsAndServices();
        startObservingDatabaseData();
        startObservingLoadingStatus();
        setLanguageDialog();
        setSwipeFunctionality();

        // Don't display swipe cards until there is data.
        setVisibilitySwipeCards(false);

        // When leaving the fragment all swipe cards are temporarily stored
        // and still available when opening the fragment again.
        if (ArticleDataStorage.temporaryArticlesExist()) {
            swipeCardsList.addAll(ArticleDataStorage.getTemporaryStoredArticles());
            swipeCardsList.get(0).initAlphaSkipButton(skip);
            swipeCardArrayAdapter.notifyDataSetChanged();
            setVisibilitySwipeCards(true);
        } else {
            loadArticlesFromDb();
        }

        return view;
    }

    /**
     * Observe if articles are currently loaded from the api or database.
     * Observe if the user changed the language.
     * React on the loading status.
     */
    public void startObservingLoadingStatus() {
        SwipeLoadingService.getLoadingApiRequest().observe(getActivity(), loading -> {
                apiIsLoading = loading;
    });

        SwipeLoadingService.getApiRequestLoadingLoadingScreen().observe(mainActivity, loading ->{
            if(loading){
                skip.setAlpha(0);
                checkInternetConnection();
            }
            handleLoadingScreen(loading, SwipeLoadingService.FIRST_TIME_LOADING);
        });

        // Loading status database.
        SwipeLoadingService.getLoadingDatabase().observe(getActivity(), loading -> {
            dbIsLoading = loading;
            setVisibilitySwipeCards(!loading);
        });

        // Show a loading screen when news of the day are loaded
        // because it prevents other database operations.
        DailyNewsLoadingService.getLoading().observe(getActivity(), loading -> {
            handleLoadingScreen(loading, DailyNewsLoadingService.LOAD_DAILY_NEWS);
        });
        // Loading status changing language.
        SwipeLoadingService.getLoadingLanguageChange().observe(getActivity(), loading -> {
            languageChangeIsLoading = loading;
            Log.d("bbupp", "Loading: " + loading);
            handleLoadingScreen(loading, SwipeLoadingService.CHANGE_LANGUAGE);
            if (loading) {
                skip.setAlpha(0);
                if(!swipeCardsList.isEmpty()){
                    ArticleDataStorage.setBackUpArticlesIfError(swipeCardsList);
                }
            }
            int visibilityAbort = loading ? Button.VISIBLE : Button.INVISIBLE;
            abortLoading.setVisibility(visibilityAbort);
        });
    }

    /**
     * Observe data in the database and store it in variables whenever it changed.
     */
    public void startObservingDatabaseData() {
        if (mainActivity != null) {
            // Observer ratings.
            ratingDbService.getAllUserPreferences().observe(mainActivity, dbCategoryRatings -> liveCategoryRatings = dbCategoryRatings);

            // Observe all topics.
            keyWordDbService.getAllKeyWords().observe(mainActivity, keyWords -> liveKeyWords = keyWords);

            // Observe the date offset for the currently selected languages.
            // Date offset is the dateTo value you send to the api in a query.
            // Don't load articles newer than the last one the user looked at.
            DateOffsetDataStorage.resetData();
            LiveData<List<LanguageCombinationRoomModel>> allLanguageCombinationsLiveData = languageComboDbService.getAll();
            allLanguageCombinationsLiveData.observe(mainActivity, languageCombinations -> {
                boolean[] currentSelection = LanguageSettingsService.loadChecked(mainActivity);
                int languageCombinationId = LanguageCombinationService.getIdOfLanguageCombination(languageCombinations, currentSelection);
                LiveData<List<RequestOffsetRoomModel>> dateOffsetsLiveData = dateOffsetDbService.getAll();
                dateOffsetsLiveData.observe(mainActivity, allOffsets ->{
                    List<RequestOffsetRoomModel> dateOffsetsForLanguageId =
                            DateOffsetService.getOffsetsForLanguageCombinationId(
                                    allOffsets ,
                                    languageCombinationId
                            );
                    // If the language is changed this observable is observed once again at another place
                    // Avoid setting data twice at this time.
                    if(!languageChangeIsLoading){
                        DateOffsetDataStorage.setDateOffsets(dateOffsetsForLanguageId);
                    }
                });
            });
        }
    }

    /**
     * Check if we have swipe articles in the database.
     * If yes add them to the view, if no call another method to load them
     * from the api.
     */
    public void loadArticlesFromDb() {
        LiveData<List<NewsArticleRoomModel>> unreadArticlesLiveData = newsArticleDbService.getAllUnreadSwipeArticles();
        unreadArticlesLiveData.observe(
                getActivity(),
                new Observer<List<NewsArticleRoomModel>>() {
                    @Override
                    public void onChanged(@Nullable List<NewsArticleRoomModel> articleModels) {
                        if (articleModels.isEmpty()) {
                            SwipeLoadingService.setLoadingApiRequest(true);
                            SwipeLoadingService.setApiRequestLoadingLoadingScreen(true);
                            loadArticlesFromApi();
                        } else {
                            SwipeLoadingService.setLoadingDatabase(true);
                            swipeCardsList.addAll(newsArticleDbService.createNewsArticleList(articleModels));
                            onDbArticlesLoaded();
                        }
                        unreadArticlesLiveData.removeObserver(this);
                    }
                });
    }

    /**
     * Called from loadArticlesFromDb() as soon as we received articles from the database.
     * Add question cards to the list of articles and set database loading to false.
     */
    private void onDbArticlesLoaded() {
        LiveData<List<KeyWordRoomModel>> allTopicsLiveData = keyWordDbService.getAllKeyWords();
        allTopicsLiveData.observe(mainActivity, new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topics) {
                if (!topics.isEmpty()) {
                    QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeCardsList, liveKeyWords);
                    swipeCardArrayAdapter.notifyDataSetChanged();
                    SwipeLoadingService.setLoadingDatabase(false);
                    allTopicsLiveData.removeObserver(this);
                }
            }
        });
    }

    /**
     * Request new articles from the api, store them in the database.
     * Add question cards to the list of articles, remove duplicate articles.
     * Reload this fragment.
     */
    public void loadArticlesFromApi() {
//        if(SwipeTimeService.dataIsLoadedTheFirstTime(mainActivity)){
//            SwipeLoadingService.setApiRequestLoadingLoadingScreen(true);
//            SwipeTimeService.setDataIsLoadedTheFirstTime(mainActivity, false);
//        }
        SwipeLoadingService.setLoadingApiRequest(true);
        canShowTooManyRequestDialogue = true;
        Thread thread = new Thread(() -> {
            try {
                LinkedList<NewsArticle> apiArticlesToAdd =
                        SwipeApiService.getAllArticlesApiForSwipeCards(
                                SwipeFragment.this,
                                liveCategoryRatings
                        );
                mainActivity.runOnUiThread(() -> {
                    storeArticlesInDatabase(apiArticlesToAdd);
                    swipeCardsList.addAll(apiArticlesToAdd);
                    CollectionService.removeDuplicatesArticleList(swipeCardsList);
                    QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeCardsList, liveKeyWords);
                    SwipeLoadingService.resetLoading();
                    reloadFragment();
                });
            } catch (Exception e) {
                Log.d("catchedError", "ERROR: " + e.toString());
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /**
     * Deletes all articles that belong to the swipe fragment from the database.
     * When they are deleted the callback function onSwipeArticlesDeleted is called
     * where the new articles are stored in the database.
     * The data that onSwipeArticlesDeleted receives is defined here.
     *
     * @param articles The articles to store in the database.
     */
    public void storeArticlesInDatabase(LinkedList<NewsArticle> articles) {
        if (mainActivity != null) {
            DeleteData deleteData = new DeleteData();
            deleteData.deletesArticle = this;
            deleteData.data = articles;
            NewsArticleDbService.getInstance(mainActivity.getApplication())
                    .deleteAllSwipedArticles(deleteData);
        }
    }

    /**
     * Called when all swipe articles from the database are deleted.
     * Inserts all articles that were passed to the delete operation as data
     * into the database.
     *
     * @param deleteData
     */
    @Override
    public void onSwipeArticlesDeleted(DeleteData deleteData) {
        if (mainActivity != null) {
            LinkedList<NewsArticle> passedArticlesToStore = (LinkedList<NewsArticle>) deleteData.data;
            newsArticleDbService.insertNewsArticles(passedArticlesToStore);
        }
    }

    /**
     * Sets the functionality for the flingContainer which handles the functionality
     * if the user swipes to the left or right or clicks on a swipe card.
     */
    public void setSwipeFunctionality() {
        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.swipe_card);
        flingContainer.setAdapter(swipeCardArrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if (swipeCardsList.size() == 0 || swipeCardsList.size() == 1) {
                    swipeCardsList.add(new ErrorSwipeCard());
                }
                swipeCardsList.remove(0);
                Log.d("LEFTT", ": " + swipeCardsList.size());
                if(swipeCardsList.size() > 0){
                    swipeCardsList.get(0).initAlphaSkipButton(skip);
                }

                boolean alreadyLoadingData = apiIsLoading || dbIsLoading;
                if (swipeCardsList.size() <= ARTICLE_MINIMUM_BEFORE_LOADING && !alreadyLoadingData) {
                    loadArticlesFromApi();
                }
                swipeCardArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                final ISwipeCard swipedCard = (ISwipeCard) dataObject;
                swipedCard.dislike(SwipeFragment.this);
                leftIndicator.setAlpha(0);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                final ISwipeCard swipedCard = (ISwipeCard) dataObject;
                swipedCard.like(SwipeFragment.this);
                rightIndicator.setAlpha(0);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // didn't always work so implemented elsewhere
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                if (!swipeCardsList.isEmpty()) {
                    ISwipeCard currentCard = swipeCardsList.get(0);
                    // Handle visibility of skip button and the like/dislike text at the bottom.
                    currentCard.onSwipe(SwipeFragment.this, scrollProgressPercent);
                }
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
                ISwipeCard clickedArticle = (ISwipeCard) dataObject;
                clickedArticle.onClick(mainActivity);
            }
        });
    }

    public void initObjectsAndServices() {
        abortLoading = view.findViewById(R.id.button_abort_language_loading);
        abortLoading.setOnClickListener(view -> abortLanguageChange());
        skip = view.findViewById(R.id.skip_button);
        skip.setOnClickListener(view -> {
            if(!swipeCardsList.isEmpty()){
                swipeCardsList.remove(0);
            }
            reloadFragment();
        });

        swipeCardsList = new ArrayList<>();
        swipeCardArrayAdapter = new NewsArticleAdapter(getActivity(), R.layout.swipe_card, swipeCardsList);
        // Only add introduction card when the user just started the app.
        if (mainActivity.showIntroductionCard()) {
            swipeCardsList.add(new IntroductionSwipeCard());
            swipeCardsList.get(0).initAlphaSkipButton(skip);
            mainActivity.introductionCardWasShown();
        }

        leftIndicator = view.findViewById(R.id.swipe_left_indicator);
        rightIndicator = view.findViewById(R.id.swipe_right_indicator);

        ratingDbService = RatingDbService.getInstance(mainActivity.getApplication());
        newsArticleDbService = NewsArticleDbService.getInstance(mainActivity.getApplication());
        keyWordDbService = KeyWordDbService.getInstance(mainActivity.getApplication());
        dateOffsetDbService = OffsetDbService.getInstance(mainActivity.getApplication());
        languageComboDbService = LanguageCombinationDbService.getInstance(mainActivity.getApplication());
        newsHistoryDbService = NewsHistoryDbService.getInstance(mainActivity.getApplication());
        setSwipeFunctionality();
    }

    /**
     * When the user clicks on the abort language change button in the loading screen.
     * Set loading false, add a backup of the previous articles to the view.
     * Show an error toast. Reload this fragment.
     */
    public void abortLanguageChange(){
        Log.d("bbuppp", "abortLanguageChange()");
        boolean[] restored = LanguageSelectionDataStorage.restorePreviousLanguageSelection();
        swipeCardsList.addAll(ArticleDataStorage.getBackUpArticlesIfError());
        swipeCardArrayAdapter.notifyDataSetChanged();
        LanguageSettingsService.saveChecked(
                mainActivity,
                restored
        );

        // Sometimes reload started before restoring the language
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SwipeLoadingService.setLoadingLanguageChange(false);
        reloadFragment();
    }

    /**
     * Set the OnClickListener for the button to select a language.
     * When a new language is selected and confirmed load new
     * articles from the api.
     */
    public void setLanguageDialog() {
        Button button = view.findViewById(R.id.button_languages);
        button.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new
                    AlertDialog.Builder(mainActivity);
            dialog.setTitle(R.string.language_change_title);
            dialog.setCancelable(false);

            final String[] languageItems = LanguageSettingsService.languageItems;
            final boolean[] initialSelection = LanguageSettingsService.loadChecked(mainActivity);
            LanguageSelectionDataStorage.backUpPreviousLanguageSelection(initialSelection);
            final boolean[] currentSelection = LanguageSettingsService.loadChecked(mainActivity);

            dialog.setMultiChoiceItems(languageItems, currentSelection, (dialogInterface, position, isChecked) -> {
                currentSelection[position] = isChecked;
                LanguageSettingsService.saveChecked(mainActivity, currentSelection);
            });

            dialog.setPositiveButton(R.string.language_change_confirm, (positiveDialog, which) -> {
                if (LanguageSettingsService.userChangedLanguage(initialSelection, currentSelection)) {
                    checkInternetConnectionToast(SwipeLoadingService.CHANGE_LANGUAGE);
                    SwipeLoadingService.setLoadingLanguageChange(true);
                    LiveData<List<LanguageCombinationRoomModel>> allLanguageCombinationsLiveData
                            = languageComboDbService.getAll();
                    allLanguageCombinationsLiveData.observe(
                            mainActivity,
                            new Observer<List<LanguageCombinationRoomModel>>() {
                        @Override
                        public void onChanged(@Nullable List<LanguageCombinationRoomModel> languageCombinations) {
                            int languageCombinationId =
                                    LanguageCombinationService.getIdOfLanguageCombination(
                                            languageCombinations, currentSelection
                                    );
                            loadArticlesForOtherLanguage(languageCombinationId);
                            allLanguageCombinationsLiveData.removeObserver(this);
                            }
                    });
                }
                positiveDialog.cancel();
            });

            dialog.setNegativeButton(R.string.language_change_cancel, (negativeDialog, which) -> {
                LanguageSettingsService.saveChecked(mainActivity, initialSelection);
                negativeDialog.cancel();
            });

            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        });
    }

    /**
     * Get the current date offsets that belong to the id of the newly selected
     * language combination.
     * Store the date offsets (date to) so the when the api to the query is formed the values
     * can be retrieved.
     * @param languageCombinationId The id of the newly selected language combination
     *                              in the database.
     */
    public void loadArticlesForOtherLanguage(int languageCombinationId) {
        if(mainActivity !=null) {
            DateOffsetDataStorage.resetData();
            // Invalid language combination
            if(languageCombinationId < 0){
                swipeCardsList.clear();
                loadArticlesFromApi();
            }
            // Valid language combination
            else{
                // Load date offsets (dateTo query parameter)
                // so they are up to date before requesting new articles.
                LiveData<List<RequestOffsetRoomModel>> dateOffsetsLiveData
                        = dateOffsetDbService.getAll();
                dateOffsetsLiveData.observe(mainActivity, new Observer<List<RequestOffsetRoomModel>>() {
                    @Override
                    public void onChanged(@Nullable List<RequestOffsetRoomModel> offsets) {
                        List<RequestOffsetRoomModel> dateOffsetsForLanguageCombination =
                                DateOffsetService.getOffsetsForLanguageCombinationId(
                                        offsets,
                                        languageCombinationId
                                );

                        // No available date offsets for this language combination.
                        if(dateOffsetsForLanguageCombination.isEmpty()){
                            DateOffsetDataStorage.resetData();
                        }
                        // We found date offsets, store them so they can be retrieved
                        // when requesting articles.
                        else{
                            DateOffsetDataStorage.setDateOffsets(dateOffsetsForLanguageCombination);
                        }
                        swipeCardsList.clear();
                        loadArticlesFromApi();
                        dateOffsetsLiveData.removeObserver(this);
                    }
                });
            }
        }
    }

    /**
     * Open this fragment once again to reset the view.
     */
    public void reloadFragment(){
        mainActivity.loadFragment(R.id.nav_swipe);
    }

    /**
     * Store the articles we currently display in the swipe cards temporarily
     * in a service to retrieve them when we return to this fragment.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        ArticleDataStorage.clearData();
        ArticleDataStorage.storeArticlesTemporarily(this.swipeCardsList);
    }

    /**
     * If we are loading data it displays a loading gif and an info text about
     * what we are loading.
     * @param loading
     * @param loadingType
     */
    public void handleLoadingScreen(boolean loading, int loadingType){
        setVisibilitySwipeCards(!loading);

        int visibilityLoadingViews = loading ? GifImageView.VISIBLE : GifImageView.INVISIBLE;
        GifImageView loadingGif = view.findViewById(R.id.loading);
        TextView loadingInfo = view.findViewById(R.id.loading_info);
        loadingGif.setVisibility(visibilityLoadingViews);
        loadingInfo.setVisibility(visibilityLoadingViews);

        String loadingText = LoadingService.getLoadingText(loadingType);
        loadingInfo.setText(loadingText);
    }

    /**
     * Hide or show the swipe cards and the language button in the view.
     * @param visible
     */
    private void setVisibilitySwipeCards(boolean visible){
        int visibilityContent = visible ? View.VISIBLE : View.INVISIBLE;
        view.findViewById(R.id.swipe_card).setVisibility(visibilityContent);
        view.findViewById(R.id.button_languages).setVisibility(visibilityContent);
    }

    /**
     * Callback function called when articles are requested or if we received
     * http answers with new articles to show the loading progress in the loading screen.
     * @param loadingText
     */
    @Override
    public void updateLoadingText(String loadingText) {
        mainActivity.runOnUiThread(() ->{
            TextView loadingProgress = view.findViewById(R.id.loading_progress);
            loadingProgress.setVisibility(TextView.VISIBLE);
            loadingProgress.setText(loadingText);
        });
    }

    /**
     * Show a dialogue as soon as the user swipes his first question card to the right
     * to tell him about news of the day.
     */
    public void showDailyNewsDialogue(int amountTopicsBeforeLike){
                if(amountTopicsBeforeLike == 0 && !SwipeTimeService.firstTopicWasLiked(mainActivity)){
                    SwipeTimeService.setFirstTopicWasLiked(mainActivity, true);
                    AlertDialog.Builder dialog = new
                            AlertDialog.Builder(mainActivity);
                    dialog.setTitle(R.string.daily_news_dialogue_title);
                    dialog.setMessage(R.string.daily_news_dialogue_message);
                    dialog.setPositiveButton(R.string.daily_news_dialogue_confirm, (
                            positiveDialog, which) -> positiveDialog.cancel()
                    ).create().show();
                }
                else if(amountTopicsBeforeLike >= 4
                        && !SwipeTimeService.alreadyRedirected(mainActivity)){
                    new Thread(() ->{
                        if(InternetConnectionService.isOnline()){
                            mainActivity.runOnUiThread(() ->{
                                SwipeTimeService.setRedirected(mainActivity, true);
                                mainActivity.switchNavigationDrawerItemFromTo(
                                        mainActivity.INDEX_SWIPE_MENU,
                                        mainActivity.INDEX_DAILY_MENU
                                );
                                mainActivity.loadFragment(R.id.nav_news);
                            });

                        }
                        else{
                            mainActivity.runOnUiThread(() -> showToastNoInternet(DailyNewsLoadingService.LOAD_DAILY_NEWS));
                        }
                    }).start();
                }
    }

    private void showToastNoInternet(int type){
        int message = -1;
        switch(type){
            case SwipeLoadingService.CHANGE_LANGUAGE: message = R.string.no_internet_language_change; break;
            case DailyNewsLoadingService.LOAD_DAILY_NEWS: message = R.string.no_internet_daily; break;
        }
        if(message != -1){
            Toast.makeText(mainActivity, message, Toast.LENGTH_LONG).show();
        }
    }

    private void checkInternetConnectionToast(int type){
        new Thread(() -> {
            if(!InternetConnectionService.isOnline()){
                mainActivity.runOnUiThread(() -> showToastNoInternet(type));
            }
        }).start();
    }

    public void showTooManyRequestsDialogue(){
        if(canShowTooManyRequestDialogue){
            mainActivity.runOnUiThread(() ->{
                AlertDialog.Builder dialog = new
                        AlertDialog.Builder(mainActivity);
                dialog.setTitle(R.string.too_many_requests_title);
                dialog.setMessage(R.string.too_many_requests_content);
                dialog.setPositiveButton("Ok", (positiveDialog, which) ->
                        positiveDialog.cancel()).create().show();
                canShowTooManyRequestDialogue = false;
            });
        }
    }

    private void checkInternetConnection(){
        new Thread(() ->{
            if(!InternetConnectionService.isOnline()){
                mainActivity.runOnUiThread(() ->{
                    AlertDialog.Builder dialog = new
                            AlertDialog.Builder(mainActivity);
                    dialog.setTitle(R.string.no_internet_title);
                    dialog.setCancelable(false);
                    dialog.setMessage(R.string.no_internet_content);
                    dialog.setPositiveButton(R.string.no_internet_positive_button, (positiveDialog, which) -> {
                        SwipeLoadingService.setApiRequestLoadingLoadingScreen(false);
                        loadArticlesFromApi();
                        SwipeLoadingService.setApiRequestLoadingLoadingScreen(true);
                        positiveDialog.cancel();
                    }).create().show();
                });
            }
        }).start();
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
     * Check the response code that the http function passes to this function
     * and show a corresponding dialogue if something went wrong
     * @param httpRequestInfo
     */
    @Override
    public void httpResultCallback(HttpRequestInfo httpRequestInfo) {
        if(httpRequestInfo.getHttpResponseCode() == HttpRequestInfo.TOO_MANY_REQUESTS){
            showTooManyRequestsDialogue();
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
