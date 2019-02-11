package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.viewElements.StatisticsFragmentDimensions;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.api.NewsApiUtils;
import com.example.rapha.swipeprototype2.customAdapters.NewsOfTheDayListAdapter;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.newsCategories.QueryWordTransformation;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.utils.HttpRequest;
import com.example.rapha.swipeprototype2.utils.IHttpRequester;
import com.example.rapha.swipeprototype2.utils.ListService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsOfTheDayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsOfTheDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsOfTheDayFragment extends Fragment implements IKeyWordProvider, IHttpRequester {

    View view;
    List<KeyWordRoomModel> topicsToLookFor;
    ArrayList<NewsArticle> articlesOfTheDay = new ArrayList();
    ListView articleListView;
    NewsOfTheDayListAdapter adapter;
    boolean isLoading;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsOfTheDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsOfTheDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsOfTheDayFragment newInstance(String param1, String param2) {
        NewsOfTheDayFragment fragment = new NewsOfTheDayFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("oftheday", "loadNewsTopics()");
        loadNewsTopics();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_of_the_day, container, false);
        articleListView = view.findViewById(R.id.articleList);
        adapter = new NewsOfTheDayListAdapter(getActivity(), R.layout.news_of_the_day_list_item, articlesOfTheDay);
        articleListView.setAdapter(adapter);

        if(noArticles()){
            isLoading = true;
            handleLoading();
        }
        return view;
    }

    public boolean noArticles(){
        return articlesOfTheDay.size() == 0;
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

    @Override
    public List<KeyWordRoomModel> getCurrentKeyWords() {
        return null;
    }

    @Override
    public void afterHttp(JSONObject newsArticleJson) {
        Log.d("oftheday", "afterHttp");
        try {
            LinkedList<NewsArticle> fetchedArticles = NewsApiUtils.jsonToNewsArticleArray(newsArticleJson, 1);
            if (fetchedArticles.size() > 0) {
                articlesOfTheDay.add(fetchedArticles.get(0));
                Log.d("oftheday2", "add article: " + fetchedArticles.get(0).title);
                adapter.notifyDataSetChanged();
                setListViewHeightBasedOnItems(articleListView);
                isLoading = false;
                handleLoading();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLoading(){
        int visibility = !isLoading ? FrameLayout.INVISIBLE : FrameLayout.VISIBLE;
        GifImageView loadingGif = view.findViewById(R.id.news_of_the_day_loading);
        loadingGif.setVisibility(visibility);
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

    public void loadNewsTopics(){
        Log.d("oftheday", "inside loadNewsTopics()");
        KeyWordDbService keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
        android.arch.lifecycle.Observer observer = getObserverToRequestArticles(keyWordDbService);
        keyWordDbService.getAllLikedKeyWords().observe(getActivity(), observer);
    }

    public android.arch.lifecycle.Observer getObserverToRequestArticles(KeyWordDbService keyWordDbService){
        HttpRequest httpRequest = new HttpRequest(NewsOfTheDayFragment.this, 0);
        android.arch.lifecycle.Observer<List<KeyWordRoomModel>> observer = new android.arch.lifecycle.Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> keyWordRoomModels) {
                Log.d("oftheday", "Observer on changed: ");
                topicsToLookFor = keyWordRoomModels;
                Log.d("oftheday", "topics1: " + topicsToLookFor.size());
                topicsToLookFor = (List<KeyWordRoomModel>)ListService.removeAllEntriesStartingAt(topicsToLookFor,10);
                Log.d("oftheday", "topics2: " + topicsToLookFor.size());
                for(int i = 0; i < topicsToLookFor.size(); i++) {
                    String[] keyWords = getKeyWordsFromTopics(topicsToLookFor.get(i));
                    try {
                        ApiService.getArticlesNewsApiByKeyWords(
                                httpRequest, NewsOfTheDayFragment.this, keyWords, LanguageSettingsService.INDEX_ENGLISH
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("oftheday", "Query error: " + e.toString());
                    }
                }
                keyWordDbService.getAllLikedKeyWords().removeObserver(this);
            }
        };
        return observer;
    }

    public String[] getKeyWordsFromTopics(KeyWordRoomModel topicToLookFor){
        List<KeyWordRoomModel> transformedKeyWords = new QueryWordTransformation().transformQueryStrings(topicToLookFor);
        String[] keyWords = new String[transformedKeyWords.size()];
        for(int k = 0; k < keyWords.length; k++){
            keyWords[k] = transformedKeyWords.get(k).keyWord;
        }
        return keyWords;
    }


    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


}
