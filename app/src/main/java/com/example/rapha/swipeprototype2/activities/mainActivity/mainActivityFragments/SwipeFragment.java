package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.rapha.swipeprototype2.activities.articleDetailActivity.ArticleDetailScrollingActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates.ArticlesNotLoadedState;
import com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates.IMainActivityState;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.categoryDistribution.CategoryRatingService;
import com.example.rapha.swipeprototype2.customAdapters.NewsArticleAdapter;
import com.example.rapha.swipeprototype2.languageSettings.LanguageSettingsService;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.RatingDbService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.utils.Logging;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SwipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SwipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeFragment extends Fragment {

    MainActivity mainActivity;
    View view;

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

    public RatingDbService dbService;
    public IMainActivityState swipeActivityState;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_swipe, container, false);
        this.swipeActivityState = new ArticlesNotLoadedState(this);
        init();
        setLanguageDialog();
        setSwipeFunctionality();

        // Set visible when article data is here
        view.findViewById(R.id.frame).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.button_languages).setVisibility(View.INVISIBLE);

        dbService.getAllUserPreferences().observe(mainActivity, new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> userPreferenceRoomModels) {
                liveUserPreferences = userPreferenceRoomModels;
                for(int i = 0; i < userPreferenceRoomModels.size(); i++){
                    Log.d("RATINGLIVE", userPreferenceRoomModels.get(i).toString());
                }
                swipeActivityState.loadArticlesFromApi(userPreferenceRoomModels);
            }
        });

        return view;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

        public void init(){
        articlesArrayList = new ArrayList<>();
        // Add empty article to show while real articles are being requested from the api.
        // TODO: wait until real articles are loaded / use caching
        NewsArticle firstCard = new NewsArticle();
        firstCard.isDefault = true;
        articlesArrayList.add(firstCard);
        articlesArrayAdapter = new NewsArticleAdapter(getActivity(), R.layout.swipe_card, articlesArrayList);
        dbService = RatingDbService.getInstance(getActivity().getApplication());
    }


    /**
     * Set the OnClickListener for the language button so it displays
     * an alert dialog that makes it possible for the user to select in which languages
     * he wants to see his news.
     */
    public void setLanguageDialog(){
        Button button = view.findViewById(R.id.button_languages);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new
                        AlertDialog.Builder(mainActivity);
                dialog.setTitle("Select languages");
                final String[] languageItems = LanguageSettingsService.languageItems;
                final boolean[] checkedItems = LanguageSettingsService.loadChecked(mainActivity);
                dialog.setMultiChoiceItems(languageItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener()  {
                    @Override
                    public void onClick(DialogInterface var1, int which, boolean isChecked){
                        checkedItems[which] = isChecked;
                        LanguageSettingsService.saveChecked(mainActivity, checkedItems);
                    }
                });
                dialog.setPositiveButton("Confirm choice", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }

    /**
     * Calls the ApiService to receive all news articles and adds them to "articlesArrayList"
     * which shows them on the cards to the user.
     */
    public void loadArticles(final List<UserPreferenceRoomModel> userPreferenceRoomModels){
        Log.d("RESTRUCTURE", "beginning load articles");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("RESTRUCTURE", "inside thread");
                    // Clean previous data if it exists.
                    newsArticlesToSwipe = new LinkedList<>();
                    // Load articles.
                    newsArticlesToSwipe = ApiService.getAllArticlesNewsApi(mainActivity, userPreferenceRoomModels);
                    // TODO: don'actionBarDrawerToggle load all images at once, the application can'actionBarDrawerToggle handle it!
                    // ArticleImageService.setImagesForTextView(newsArticlesToSwipe, 0);
                    Log.d("AMOUNT", "news articles loaded: " + newsArticlesToSwipe.size());
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("RESTRUCTURE", "inside runOnUiThread 1");
                            swipeActivityState.articlesAreLoaded();
                            addArticlesToView();
                            view.findViewById(R.id.frame).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.button_languages).setVisibility(View.VISIBLE);
                            Log.d("RESTRUCTURE", "inside runOnUiThread 2");
                        }
                    });
                } catch (Exception e) {
                    Log.d("RESTRUCTURE", "inside catch thread");
                    e.printStackTrace();
                    Log.e("HTTPERROR", e.toString());
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
        Log.d("RESTRUCTURE", "addArticlesToView");
        articlesArrayList.addAll(newsArticlesToSwipe);
        articlesArrayAdapter.notifyDataSetChanged();

        // Pseudo functionality to show when the articles are loaded.
        TextView textView = view.findViewById(R.id.itemText);
        textView.setText("Start swiping to read articles!\n\n " +
                "Swipe interesting articles to the right\n\n " +
                "Swipe articles that aren'actionBarDrawerToggle interesting to the left");
        Logging.logAmountOfArticles(mainActivity);
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
                articlesArrayList.remove(0);
                // Handled here because every swiped card is removed here.
                swipeActivityState.handleArticlesOnEmpty();
                articlesArrayAdapter.notifyDataSetChanged();
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onLeftCardExit(Object dataObject) {
                NewsArticle swipedArticle = (NewsArticle)dataObject;
                CategoryRatingService.rateAsNotInteresting(liveUserPreferences, SwipeFragment.this, swipedArticle);
            }

            /**
             * Gives the swiped news card a minus rating in the database.
             * @param dataObject The swiped news card.
             */
            @Override
            public void onRightCardExit(Object dataObject) {
                final NewsArticle swipedArticle = (NewsArticle)dataObject;
                CategoryRatingService.rateAsInteresting(liveUserPreferences, SwipeFragment.this, swipedArticle);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // didn'actionBarDrawerToggle always work so implemented elsewhere
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
                Intent intent = new Intent(mainActivity, ArticleDetailScrollingActivity.class);
                intent.putExtra("clickedArticle", (NewsArticle)dataObject);
                startActivity(intent);
            }
        });
    }


}
