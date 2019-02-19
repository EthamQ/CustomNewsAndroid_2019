package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
import com.example.rapha.swipeprototype2.dataStorage.ArticleDataStorage;
import com.example.rapha.swipeprototype2.dataStorage.DateOffsetDataStorage;
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
public class SwipeFragment extends Fragment implements IKeyWordProvider, IDeletesArticle {

    public MainActivity mainActivity;
    public View view;

    // When this amount of articles is leftIndicator in
    // swipeCardsList we load new articles from the api
    public static final int articlesAmountLoad = 10;

    // How many articles to load in the beginning from the db.
    public static final int articlesAmountLoadFromDb = ApiService.MAX_NUMBER_OF_ARTICLES;

    // Adapter for the fling Container (swipe functionality)
    public NewsArticleAdapter swipeCardArrayAdapter;

    // "swipeCardsList" is added to the "swipeCardArrayAdapter" and contains
    // the news articles to be displayed
    public ArrayList<ISwipeCard> swipeCardsList;

    // Contains all the user preferences fetched from the database (news category and its rating).
    public List<UserPreferenceRoomModel> liveCategoryRatings;
    public List<KeyWordRoomModel> liveKeyWords;

    public RatingDbService ratingDbService;
    public NewsArticleDbService newsArticleDbService;
    public KeyWordDbService keyWordDbService;
    public OffsetDbService dateOffsetDbService;
    public LanguageCombinationDbService languageComboDbService;

    public boolean shouldReloadFragment = false;
    public boolean apiIsLoading = false;
    boolean dbIsLoading = false;
    public boolean languageChangeIsLoading = false;
    public boolean languageShouldBeSwitched = false;

    boolean[] previousLanguageSelection;
    LinkedList<NewsArticle> remainingArticlesBeforeLoadingNewOnes = new LinkedList<>();

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
        setVisibilitySwipeCards(false);

        if(ArticleDataStorage.temporaryArticlesExist()){
            swipeCardsList.addAll(ArticleDataStorage.getTemporaryStoredArticles());
            swipeCardArrayAdapter.notifyDataSetChanged();
            setVisibilitySwipeCards(true);
        } else{ loadFromDb(); }

        return view;
    }


    public void startObservingLoadingStatus(){
        DailyNewsLoadingService.getLoading().observe(getActivity(),
                loading -> handleLoadingScreen(loading, DailyNewsLoadingService.LOAD_DAILY_NEWS)
        );
        SwipeLoadingService.getLoadingApiRequest().observe(getActivity(), loading -> apiIsLoading = loading);
        SwipeLoadingService.getLoadingLanguageChange().observe(getActivity(), loading -> {
            languageChangeIsLoading = loading;
            handleLoadingScreen(loading, SwipeLoadingService.CHANGE_LANGUAGE);
            if(loading){
                new Thread(() -> {
                        try {
                            Thread.sleep(LoadingService.MAX_LOADING_TIME_MILLS);
                            if(languageChangeIsLoading){
                                mainActivity.runOnUiThread(() -> {
                                    SwipeLoadingService.setLoadingLanguageChange(false);
                                    swipeCardsList.addAll(ArticleDataStorage.getBackUpArticlesIfError());
                                    swipeCardArrayAdapter.notifyDataSetChanged();
                                    LanguageSettingsService.saveChecked(mainActivity, previousLanguageSelection);
                                    Context context = mainActivity.getApplicationContext();
                                    CharSequence text = "Sorry something went wrong, please try it again later or check your internet connection";
                                    int duration = Toast.LENGTH_LONG;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    reloadFragment();
                                });
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }).start();
            }
        });
        SwipeLoadingService.getLoadingDatabase().observe(getActivity(), loading ->{
            dbIsLoading = loading;
            setVisibilitySwipeCards(!loading);
        });
    }




    public void startObservingDatabaseData(){
        ratingDbService.getAllUserPreferences().observe(mainActivity, dbCategoryRatings -> liveCategoryRatings = dbCategoryRatings);
        keyWordDbService.getAllKeyWords().observe(mainActivity, keyWords -> liveKeyWords = keyWords);
        keepOffsetUpToDate();
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
                                SwipeLoadingService.setLoadingApiRequest(true);
                                loadFromApi();
                                newsArticleDbService.getAllUnreadSwipeArticles().removeObserver(this);
                            }
                            else{
                                SwipeLoadingService.setLoadingDatabase(true);
                                swipeCardsList.addAll(newsArticleDbService.createNewsArticleList(articleModels, articleModels.size()));
                                keyWordDbService.getAllKeyWords().observe(mainActivity, new Observer<List<KeyWordRoomModel>>() {
                                    @Override
                                    public void onChanged(@Nullable List<KeyWordRoomModel> keyWords) {
                                            if(!keyWords.isEmpty()){
                                                QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeCardsList, liveKeyWords);
                                                swipeCardArrayAdapter.notifyDataSetChanged();
                                                newsArticleDbService.getAllUnreadSwipeArticles().removeObserver(articleObserver);
                                                keyWordDbService.getAllKeyWords().removeObserver(this);
                                                SwipeLoadingService.setLoadingDatabase(false);
                                                setVisibilitySwipeCards(true);
                                            }
                                    }
                                });
                            }
                        }
                    });
    }

    public void loadFromApi(){
        SwipeLoadingService.setLoadingApiRequest(true);
        Log.d("newswipe", "loadFromApi()");
            Thread thread = new Thread(() -> {
                    try {
                        LinkedList<NewsArticle> apiArticlesToAdd =
                                ApiService.getAllArticlesNewsApi(SwipeFragment.this, liveCategoryRatings);
                        Log.d("newswipe", "articles from api call: " + apiArticlesToAdd.size());
                        Log.d("newswipe", "first article from api call: " + apiArticlesToAdd.get(0).toString());
                        mainActivity.runOnUiThread(() -> {
                            storeArticlesInDatabase(apiArticlesToAdd);
                            swipeCardsList.addAll(apiArticlesToAdd);
                            swipeCardsList.addAll(apiArticlesToAdd);
                            CollectionService.removeDuplicatesArticleList(swipeCardsList);
                            Logging.logSwipeCards(swipeCardsList, "newswipe2");
                            QuestionCardService.mixQuestionCardsIntoSwipeCards(swipeCardsList, liveKeyWords);
                            SwipeLoadingService.setLoadingLanguageChange(false);
                            SwipeLoadingService.setLoadingApiRequest(false);
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
     * if the user swipes to the leftIndicator or rightIndicator etc.
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
                if(swipeCardsList.size() <= articlesAmountLoad && !apiIsLoading && !dbIsLoading){
                    for(int i = 0; i < swipeCardsList.size(); i++){
                        if(swipeCardsList.get(i) instanceof NewsArticle){
                            remainingArticlesBeforeLoadingNewOnes.add((NewsArticle)swipeCardsList.get(i));
                        }
                    }
                    loadFromApi();
                }
                swipeCardArrayAdapter.notifyDataSetChanged();
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onLeftCardExit(Object dataObject) {
                final ISwipeCard swipedCard = (ISwipeCard)dataObject;
                swipedCard.dislike(SwipeFragment.this);
                leftIndicator.setAlpha(0);
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
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

    @Override
    public List<KeyWordRoomModel> getCurrentKeyWords() {
        return liveKeyWords;
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
            previousLanguageSelection = initialSelection;
            final boolean[] currentSelection = LanguageSettingsService.loadChecked(mainActivity);

            dialog.setMultiChoiceItems(languageItems, currentSelection, (dialogInterface, position, isChecked) ->   {
                currentSelection[position] = isChecked;
                LanguageSettingsService.saveChecked(mainActivity, currentSelection);
            });

            dialog.setPositiveButton("Confirm choice", (positiveDialog, which) -> {
                if(LanguageSettingsService.userChangedLanguage(initialSelection, currentSelection)){
                    SwipeLoadingService.setLoadingLanguageChange(true);
                    languageShouldBeSwitched = true;
                    keepOffsetUpToDate();
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

    public void keepOffsetUpToDate(){
        DateOffsetDataStorage.resetData();
        boolean[] currentLanguages = LanguageSettingsService.loadChecked(mainActivity);
        dateOffsetDbService.getOffsetsForLanguageCombination(mainActivity, currentLanguages).observe(getActivity(), offsets -> {
            for(int j = 0; j < offsets.size(); j++){
                // Offset data is retrieved when the http requests are build.
                DateOffsetDataStorage.setOffsetForCategory(offsets.get(j).categoryId, offsets.get(j).requestOffset);

                // Before switching the language you need current offset data.
                // When switching the language keepOffsetUpToDate() is called.
                boolean lastIteration = j == offsets.size() - 1;
                if(languageShouldBeSwitched && lastIteration){
                    startSwitchingLanguage();
                }
            }
        });
    }

    private synchronized void startSwitchingLanguage(){
        if(languageShouldBeSwitched){
            languageShouldBeSwitched = false;
            ArticleDataStorage.setBackUpArticlesIfError(swipeCardsList);
            swipeCardsList.clear();
            shouldReloadFragment = true;
            loadFromApi();
        }
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
