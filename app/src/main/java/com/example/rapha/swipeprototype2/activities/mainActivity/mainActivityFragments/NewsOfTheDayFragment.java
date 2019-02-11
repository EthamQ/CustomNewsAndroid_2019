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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.arch.lifecycle.Observer;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.viewElements.DimensionService;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.api.NewsApiUtils;
import com.example.rapha.swipeprototype2.customAdapters.NewsOfTheDayListAdapter;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.newsCategories.QueryWordTransformation;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.http.HttpRequest;
import com.example.rapha.swipeprototype2.http.IHttpRequester;
import com.example.rapha.swipeprototype2.time.ApiRequestTimeService;
import com.example.rapha.swipeprototype2.utils.ListService;
import com.example.rapha.swipeprototype2.utils.Logging;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
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
public class NewsOfTheDayFragment extends Fragment implements IHttpRequester {

    View view;
    ArrayList<NewsArticle> articlesOfTheDay = new ArrayList();
    ListView articleListView;
    NewsOfTheDayListAdapter adapter;
    NewsArticleDbService newsArticleDbService;
    final int ARTICLE_MINIMUM = 5;
    // Store observer to remove it in another function.
    Observer<List<NewsArticleRoomModel>> databaseArticlesObserver;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_of_the_day, container, false);
        init();
        if(ApiRequestTimeService.forceApiReloadDaily(getActivity())){
            loadArticlesFromApi();
        } else{
            // If no db data calls loadArticlesFromApi() afterwards.
            loadArticlesFromDatabase();
            Log.d("loadDB", "loadArticlesFromDatabase() createviw");
        }
        return view;
    }

    /**
     * Called as soon as the http request to the news api received an answer.
     * @param newsArticleJson
     */
    @Override
    public void httpResultCallback(JSONObject newsArticleJson) {
        Log.d("oftheday", "httpResultCallback");

        LinkedList<NewsArticle> articlesForKeyword = new LinkedList<>();
        try {
            articlesForKeyword = NewsApiUtils.jsonToNewsArticleArray(newsArticleJson, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (articlesForKeyword.size() > 0) {
            setDateArticlesLoaded();
            handleLoading(false);
            handleLoadedApiArticleData(articlesForKeyword);
        }
    }

    private void init(){
        newsArticleDbService = NewsArticleDbService.getInstance(getActivity().getApplication());
        articleListView = view.findViewById(R.id.articleList);
        adapter = new NewsOfTheDayListAdapter(getActivity(), R.layout.news_of_the_day_list_item, articlesOfTheDay);
        articleListView.setAdapter(adapter);
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                NewsArticle clickedArticle = (NewsArticle)articleListView.getItemAtPosition(position);
                clickedArticle.onClick(getActivity());
            }
        });
    }

    private void handleLoadedApiArticleData(LinkedList<NewsArticle> articlesForKeyword){
        int entryToAdd = 0;
        NewsArticle articleToAdd = articlesForKeyword.get(entryToAdd);
        articlesOfTheDay.add(articleToAdd);
        storeArticleInDatabase(articleToAdd);
        adapter.notifyDataSetChanged();
        DimensionService.setListViewHeightBasedOnItems(articleListView);
        Log.d("oftheday2", "add article: " + articlesForKeyword.get(0).title);
    }

    private void handleLoading(boolean isLoading){
        int visibilityLoadingGif = isLoading ? FrameLayout.VISIBLE : FrameLayout.INVISIBLE;
        GifImageView loadingGif = view.findViewById(R.id.news_of_the_day_loading);
        loadingGif.setVisibility(visibilityLoadingGif);
    }

    private void loadArticlesFromApi(){
        Log.d("oftheday", "inside loadArticlesFromApi()");
        KeyWordDbService keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
        Observer observer = getObserverToRequestArticles(keyWordDbService);
        keyWordDbService.getAllLikedKeyWords().observe(getActivity(), observer);
    }

    private void loadArticlesFromDatabase(){
        Log.d("loadDB", "loadArticlesFromDatabase()");
        newsArticleDbService.getAllNewsOfTheDayArticles().observe(getActivity(), new Observer<List<NewsArticleRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<NewsArticleRoomModel> storedArticles) {
                databaseArticlesObserver = this;
                Logging.logArticleModels(storedArticles, "pff");
                Log.d("loadDB", "storedarticles: " + storedArticles.size());
                if(articlesEmpty() && storedArticles.size() > 0){
                    for(int i = 0; i < storedArticles.size(); i++){
                        articlesOfTheDay.add(newsArticleDbService.createNewsArticle(storedArticles.get(i)));
                    }
                    adapter.notifyDataSetChanged();
                    DimensionService.setListViewHeightBasedOnItems(articleListView);
                    setTextArticlesLoaded();
                    handleLoading(false);
                    Log.d("loadDB", "removeobserver1 ");
                    newsArticleDbService.getAllNewsOfTheDayArticles().removeObserver(this);
                } else if(allowedToLoadFromApi()){
                    loadArticlesFromApi();
                }
            }
        });
    }

    private boolean allowedToLoadFromApi(){
        boolean firstTime = !ApiRequestTimeService.valueIsSet(
                getActivity(),
                ApiRequestTimeService.TIME_OF_RELAOD_DAILY
        );
        boolean forceReload = ApiRequestTimeService.forceApiReloadDaily(getActivity());
        return firstTime || forceReload;
    }

    private Observer getObserverToRequestArticles(KeyWordDbService keyWordDbService){
        HttpRequest httpRequest = new HttpRequest(NewsOfTheDayFragment.this, 0);
        Observer<List<KeyWordRoomModel>> observer = new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> keyWordRoomModels) {
                if(enoughTopics(keyWordRoomModels)){
                    setTextArticlesLoaded();
                    handleLoading(true);
                    List<KeyWordRoomModel> topicsToLookFor = (List<KeyWordRoomModel>)ListService.removeAllEntriesStartingAt(keyWordRoomModels,10);
                    Log.d("oftheday", "Observer on changed: ");
                    Log.d("oftheday", "topics1: " + topicsToLookFor.size());
                    for(int i = 0; i < topicsToLookFor.size(); i++) {
                        String[] keyWords = new QueryWordTransformation().getKeyWordsFromTopics(topicsToLookFor.get(i));
                        try {
                            ApiService.getArticlesNewsApiByKeyWords(
                                    httpRequest, keyWords, LanguageSettingsService.INDEX_ENGLISH
                            );
                            if(!(databaseArticlesObserver == null)){
                                newsArticleDbService.getAllNewsOfTheDayArticles().removeObserver(databaseArticlesObserver);
                                Log.d("loadDB", "removeobserver2 ");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("oftheday", "Query error: " + e.toString());
                        }
                    }
                    keyWordDbService.getAllLikedKeyWords().removeObserver(this);
                } else{
                    handleLoading(false);
                }
            }
        };
        return observer;
    }

    private void storeArticleInDatabase(NewsArticle newsArticle){
        NewsArticleRoomModel insert = newsArticleDbService.createNewsArticleRoomModelToInsert(
                newsArticle
        );
        insert.articleType = NewsArticleRoomModel.NEWS_OF_THE_DAY;
        newsArticleDbService.insert(insert);
    }

    private boolean articlesEmpty(){
        return articlesOfTheDay.size() == 0;
    }

    private boolean enoughTopics(List<KeyWordRoomModel> topicsToLookFor){
        return topicsToLookFor.size() >= ARTICLE_MINIMUM;
    }

    private void setTextArticlesLoaded(){
        TextView belowHeadline = view.findViewById(R.id.news_of_the_day_info);
        belowHeadline.setText("Today");
    }

    private void setDateArticlesLoaded(){
        ApiRequestTimeService.saveLastLoaded(
                getActivity(),
                new Date(),
                ApiRequestTimeService.TIME_OF_RELAOD_DAILY);
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
}
