package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

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

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
import com.example.rapha.swipeprototype2.roomDatabase.requestOffset.RequestOffsetRoomModel;
import com.example.rapha.swipeprototype2.temporaryDataStorage.ArticleDataStorage;
import com.example.rapha.swipeprototype2.temporaryDataStorage.DateOffsetDataStorage;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.loading.DailyNewsLoadingService;
import com.example.rapha.swipeprototype2.loading.LoadingService;
import com.example.rapha.swipeprototype2.loading.SwipeLoadingService;
import com.example.rapha.swipeprototype2.questionCards.QuestionCardService;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.LanguageCombinationDbService;
import com.example.rapha.swipeprototype2.roomDatabase.OffsetDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
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
import com.example.rapha.swipeprototype2.temporaryDataStorage.LanguageSelectionDataStorage;
import com.example.rapha.swipeprototype2.utils.CollectionService;
import com.example.rapha.swipeprototype2.utils.Logging;
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
public class SwipeFragment extends Fragment implements IDeletesArticle {

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

    // Booleans tracking the state.
    public boolean apiIsLoading = false;
    boolean dbIsLoading = false;
    public boolean languageChangeIsLoading = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_swipe, container, false);

        initObjectsAndServices();
        setLanguageDialog();
        setSwipeFunctionality();
        startObservingDatabaseData();
        startObservingLoadingStatus();

        // Don't display swipe cards until there is data.
        setVisibilitySwipeCards(false);

        // When leaving the fragment all swipe cards are temporarily stored
        // and still available when again opening the fragment.
        if(ArticleDataStorage.temporaryArticlesExist()){
            swipeCardsList.addAll(ArticleDataStorage.getTemporaryStoredArticles());
            swipeCardArrayAdapter.notifyDataSetChanged();
            setVisibilitySwipeCards(true);
        } else{ loadArticlesFromDb(); }

        return view;
    }


    /**
     * Look if articles are loaded from the api or database.
     * Look if the user changed the language.
     * Set variables or react on the loading status.
     */
    public void startObservingLoadingStatus(){
        SwipeLoadingService.getLoadingApiRequest().observe(getActivity(), loading -> apiIsLoading = loading);

        SwipeLoadingService.getLoadingDatabase().observe(getActivity(), loading ->{
            dbIsLoading = loading;
            setVisibilitySwipeCards(!loading);
        });

        // Show a loading screen when news of the day are loaded
        // because it prevents other database operations.
        DailyNewsLoadingService.getLoading().observe(getActivity(),
                loading -> handleLoadingScreen(loading, DailyNewsLoadingService.LOAD_DAILY_NEWS)
        );

        SwipeLoadingService.getLoadingLanguageChange().observe(getActivity(), loading -> {
            languageChangeIsLoading = loading;
            handleLoadingScreen(loading, SwipeLoadingService.CHANGE_LANGUAGE);
            if(loading){
                SwipeLoadingService.reactOnLanguageChangeUnsuccessful(this);
            }
        });
    }

    public void startObservingDatabaseData(){
        if(mainActivity != null){
            // Observer ratings
            ratingDbService.getAllUserPreferences().observe(mainActivity, dbCategoryRatings -> liveCategoryRatings = dbCategoryRatings);

            // Observe all topics
            keyWordDbService.getAllKeyWords().observe(mainActivity, keyWords -> liveKeyWords = keyWords);

            // Observer date offsets for current language selection
            DateOffsetDataStorage.resetData();
            boolean[] currentLanguages = LanguageSettingsService.loadChecked(mainActivity);
            dateOffsetDbService.getOffsetsForLanguageCombination(mainActivity, currentLanguages)
                    .observe(mainActivity, offsets -> {
                        if(!offsets.isEmpty()){
                            DateOffsetDataStorage.setDateOffsets(offsets);
                        }
                    });
        }
    }

    public void loadArticlesFromDb() {
        Log.d("newswipe", "loadArticlesFromDb()");
        LiveData<List<NewsArticleRoomModel>> unreadArticlesLiveData = newsArticleDbService.getAllUnreadSwipeArticles();
        unreadArticlesLiveData.observe(
                getActivity(),
                new Observer<List<NewsArticleRoomModel>>() {
                    @Override
                    public void onChanged(@Nullable List<NewsArticleRoomModel> articleModels) {
                        final Observer articleObserver = this;
                        Log.d("newswipe", "loadArticlesFromDb() onchanged");
                        Log.d("newswipe", "loadArticlesFromDb() onchanged article size: " + articleModels.size());
                        if (articleModels.isEmpty()) {
                            SwipeLoadingService.setLoadingApiRequest(true);
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

    private void onDbArticlesLoaded(){
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

    public void loadArticlesFromApi(){
        SwipeLoadingService.setLoadingApiRequest(true);
        Log.d("newswipe", "loadArticlesFromApi()");
            Thread thread = new Thread(() -> {
                    try {
                        LinkedList<NewsArticle> apiArticlesToAdd =
                                ApiService.getAllArticlesNewsApi(SwipeFragment.this, liveCategoryRatings);
                        Log.d("newswipe", "articles from api call: " + apiArticlesToAdd.size());
                        mainActivity.runOnUiThread(() -> {
                            storeArticlesInDatabase(apiArticlesToAdd);
                            swipeCardsList.addAll(apiArticlesToAdd);
                            CollectionService.removeDuplicatesArticleList(swipeCardsList);
                            Logging.logSwipeCards(swipeCardsList, "newswipe2");
                            QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeCardsList, liveKeyWords);
                            SwipeLoadingService.resetLoading();
                            reloadFragment();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            });
            thread.start();
    }

    /**
     * Deletes all articles that belong to this fragment from the database.
     * When they are deleted onDeleted is called where the new articles are stored in the database.
     * @param articles The articles to store in the database.
     */
    public void storeArticlesInDatabase(LinkedList<NewsArticle> articles){
        if(mainActivity != null){
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
     * @param deleteData
     */
    @Override
    public void onDeleted(DeleteData deleteData) {
        if(mainActivity != null){
            NewsArticleDbService.getInstance(mainActivity.getApplication())
                    .insertNewsArticles((LinkedList<NewsArticle>) deleteData.data);
        }
    }

    /**
     * Sets the functionality for the flingContainer which handles the functionality
     * if the user swipes to the left or right or clicks on a swipe cards.
     */
    public void setSwipeFunctionality(){
        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.swipe_card);
        flingContainer.setAdapter(swipeCardArrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if(swipeCardsList.size() == 0 || swipeCardsList.size() == 1){
                    swipeCardsList.add(new ErrorSwipeCard());
                }
                Log.d("newswipe", "++++++++++++++++++++++++");
                Log.d("newswipe", "article: " + swipeCardsList.get(0).toString());
                swipeCardsList.remove(0);
                Log.d("newswipe", "articles leftIndicator: " + swipeCardsList.size());

                Log.d("newswipe", "++++++++++++++++++++++++");
                boolean alreadyLoadingData = apiIsLoading || dbIsLoading;
                if(swipeCardsList.size() <= ARTICLE_MINIMUM_BEFORE_LOADING && !alreadyLoadingData){
                    loadArticlesFromApi();
                }
                swipeCardArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                final ISwipeCard swipedCard = (ISwipeCard)dataObject;
                swipedCard.dislike(SwipeFragment.this);
                leftIndicator.setAlpha(0);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                final ISwipeCard swipedCard = (ISwipeCard)dataObject;
                swipedCard.like(SwipeFragment.this);
                rightIndicator.setAlpha(0);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // didn't always work so implemented elsewhere
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                Log.d("swipee", "on scroll detected");
                if(!swipeCardsList.isEmpty()){
                    ISwipeCard currentCard = swipeCardsList.get(0);
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
                ISwipeCard clickedArticle = (ISwipeCard)dataObject;
                clickedArticle.onClick(mainActivity);
            }
        });
    }

    public void initObjectsAndServices(){
        swipeCardsList = new ArrayList<>();
        swipeCardArrayAdapter = new NewsArticleAdapter(getActivity(), R.layout.swipe_card, swipeCardsList);
        // Only add introduction card when the user just started the app.
        if(mainActivity.showIntroductionCard()){
            swipeCardsList.add(new IntroductionSwipeCard());
            mainActivity.introductionCardWasShown();
        }

        leftIndicator = view.findViewById(R.id.swipe_left_indicator);
        rightIndicator = view.findViewById(R.id.swipe_right_indicator);

        ratingDbService = RatingDbService.getInstance(getActivity().getApplication());
        newsArticleDbService = NewsArticleDbService.getInstance(getActivity().getApplication());
        keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
        dateOffsetDbService = OffsetDbService.getInstance(getActivity().getApplication());
        languageComboDbService = LanguageCombinationDbService.getInstance(getActivity().getApplication());
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
            LanguageSelectionDataStorage.backUpPreviousLanguageSelection(initialSelection);
            final boolean[] currentSelection = LanguageSettingsService.loadChecked(mainActivity);

            dialog.setMultiChoiceItems(languageItems, currentSelection, (dialogInterface, position, isChecked) ->   {
                currentSelection[position] = isChecked;
                LanguageSettingsService.saveChecked(mainActivity, currentSelection);
            });

            dialog.setPositiveButton("Confirm choice", (positiveDialog, which) -> {
                if(LanguageSettingsService.userChangedLanguage(initialSelection, currentSelection)){
                    SwipeLoadingService.setLoadingLanguageChange(true);
                    loadArticlesForOtherLanguage();
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

    public void loadArticlesForOtherLanguage(){
        if(mainActivity != null){
            DateOffsetDataStorage.resetData();
            boolean[] currentLanguages = LanguageSettingsService.loadChecked(mainActivity);
            LiveData<LinkedList<RequestOffsetRoomModel>> dateOffsetsLiveData
                    = dateOffsetDbService.getOffsetsForLanguageCombination(mainActivity, currentLanguages);
            dateOffsetsLiveData.observe(mainActivity, new Observer<LinkedList<RequestOffsetRoomModel>>() {
                @Override
                public void onChanged(@Nullable LinkedList<RequestOffsetRoomModel> offsets) {
                    if(!offsets.isEmpty()){
                        DateOffsetDataStorage.setDateOffsets(offsets);
                        ArticleDataStorage.setBackUpArticlesIfError(swipeCardsList);
                        swipeCardsList.clear();
                        loadArticlesFromApi();
                        dateOffsetsLiveData.removeObserver(this);
                    }
                }
            });
        }
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

    private void setVisibilitySwipeCards(boolean visible){
        int visibilityContent = visible ? View.VISIBLE : View.INVISIBLE;
        view.findViewById(R.id.swipe_card).setVisibility(visibilityContent);
        view.findViewById(R.id.button_languages).setVisibility(visibilityContent);
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
