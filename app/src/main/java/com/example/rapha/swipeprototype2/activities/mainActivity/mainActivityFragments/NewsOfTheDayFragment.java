package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.arch.lifecycle.Observer;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.viewElements.DimensionService;
import com.example.rapha.swipeprototype2.customAdapters.NewsOfTheDayListAdapter;
import com.example.rapha.swipeprototype2.jobScheduler.NewsOfTheDayScheduler;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.time.ApiRequestTimeService;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.JOB_SCHEDULER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsOfTheDayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsOfTheDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsOfTheDayFragment extends Fragment {

    View view;
    ArrayList<NewsArticle> articlesOfTheDay = new ArrayList();
    ListView articleListView;
    NewsOfTheDayListAdapter adapter;
    NewsArticleDbService newsArticleDbService;
    KeyWordDbService keyWordDbService;
    public static final int ARTICLE_MINIMUM = 5;
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
        loadArticles();
        return view;
    }

    private void init(){
        newsArticleDbService = NewsArticleDbService.getInstance(getActivity().getApplication());
        keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
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

        Button debug = view.findViewById(R.id.debug_button);
        debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initArticleRequestScheduler();
            }
        });
    }

    private void handleLoading(boolean isLoading){
        int visibilityLoadingGif = isLoading ? FrameLayout.VISIBLE : FrameLayout.INVISIBLE;
        GifImageView loadingGif = view.findViewById(R.id.news_of_the_day_loading);
        loadingGif.setVisibility(visibilityLoadingGif);
    }

    private void loadArticles(){
        boolean firstTimeLoading = !ApiRequestTimeService.valueIsSetDefault(
                        getActivity(),
                        ApiRequestTimeService.TIME_OF_RELAOD_DAILY
                );
        if(firstTimeLoading){
            initArticleRequestScheduler();
        }
        else{
            loadArticlesFromDatabase();
        }
    }

    private void initArticleRequestScheduler(){
        int testValue = 15 * 60 * 1000;
        int realValue = 24 * 60 * 60 * 1000;
        ComponentName componentName = new ComponentName(this.getActivity(), NewsOfTheDayScheduler.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(testValue)
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        String TAG = "scheduler";
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "scheduler success");
        }
        else{
            Log.d(TAG, "scheduler failure");
        }
    }

    private void cancelJob(){
        String TAG = "scheduler";
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "job cancelled");
    }

    private void loadArticlesFromDatabase(){
//        Log.d("loadDB", "loadArticlesFromDatabase()");
//        newsArticleDbService.getAllDailyArticlesNotArchived().observe(getActivity(), new Observer<List<NewsArticleRoomModel>>() {
//            @Override
//            public void onChanged(@Nullable List<NewsArticleRoomModel> storedArticles) {
//                if(articlesEmpty() && storedArticles.size() >= ARTICLE_MINIMUM){
//                    for(int i = 0; i < storedArticles.size(); i++){
//                        articlesOfTheDay.add(newsArticleDbService.createNewsArticle(storedArticles.get(i)));
//                        newsArticleDbService.setAsRead(storedArticles.get(i));
//                    }
//                    adapter.notifyDataSetChanged();
//                    DimensionService.setListViewHeightBasedOnItems(articleListView);
//                    setTextArticlesLoaded();
//                    handleLoading(false);
//                    newsArticleDbService.getAllDailyArticlesNotArchived().removeObserver(this);
//                }
//            }
//        });

        // getOneArticleForEveryTopicOfTheDay();
        getTopicsOfTheDay();
    }

    private void getOneArticleForEveryTopicOfTheDay(){

        String TAG = "avoidrepeat";
        Log.d(TAG, "getOneArticleForEveryTopicOfTheDay()");
        keyWordDbService.getAllKeyWordsArticlesOfTheDay().observe(getActivity(), new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topics) {
                Observer keyWordObserver = this;
                Log.d(TAG, "onchanged topic size: " + topics.size());
                if(articlesEmpty() && topics.size() >= ARTICLE_MINIMUM){
                    for(int i = 0; i < topics.size(); i++){
                        final String currentTopic = topics.get(i).keyWord;
                        Log.d(TAG, "current topic: " + currentTopic);
                        newsArticleDbService.getAllNewsOfTheDayArticlesByKeyWord(currentTopic).observe(getActivity(), new Observer<List<NewsArticleRoomModel>>() {
                            @Override
                            public void onChanged(@Nullable List<NewsArticleRoomModel> articlesForKeyWord) {
                                Log.d(TAG, "current topic below: " + currentTopic);
                                //Log.d(TAG, "onchanged articlesforkeyword size: " + articlesForKeyWord.size());
                                if(articlesForKeyWord.size() > 0 && articlesOfTheDay.size() < topics.size()){
                                    int indexFirstEntry = 0;
                                    Log.d(TAG, "add article:: " + articlesForKeyWord.get(indexFirstEntry).hasBeenRead + ", " +articlesForKeyWord.get(indexFirstEntry).archived + ", " + articlesForKeyWord.get(indexFirstEntry).title);
                                        articlesOfTheDay.add(newsArticleDbService.createNewsArticle(articlesForKeyWord.get(indexFirstEntry)));
                                        newsArticleDbService.setAsRead(articlesForKeyWord.get(indexFirstEntry));
                                        adapter.notifyDataSetChanged();
                                        DimensionService.setListViewHeightBasedOnItems(articleListView, true);
                                        setTextArticlesLoaded();
                                        handleLoading(false);

                                    newsArticleDbService.getAllNewsOfTheDayArticlesByKeyWord(currentTopic).removeObserver(this);

                                } else if(articlesOfTheDay.size() == topics.size()){
                                    // keyWordDbService.getAllKeyWordsArticlesOfTheDay().removeObserver(keyWordObserver);
                                    keyWordDbService.getAllKeyWordsArticlesOfTheDay().removeObserver(keyWordObserver);

                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void getTopicsOfTheDay(){
        String TAG = "avoidrepeat";
        Log.d(TAG, "getOneArticleForEveryTopicOfTheDay()");
        keyWordDbService.getAllKeyWordsArticlesOfTheDay().observe(getActivity(), new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topics) {
                Log.d(TAG, "onchanged topic size: " + topics.size());
                if(articlesEmpty() && topics.size() >= ARTICLE_MINIMUM){
                    String[] topicsTemp = new String[topics.size()];
                    for(int i = 0; i < topics.size(); i++){
                        final String currentTopic = topics.get(i).keyWord;
                        topicsTemp[i] = currentTopic;
                        Log.d(TAG, "current topic1: " + currentTopic);
                    }
                    getArticlesForTopics(topicsTemp);
                    keyWordDbService.getAllKeyWordsArticlesOfTheDay().removeObserver(this);
                }
            }
        });
    }

    public void getArticlesForTopics(String[] topics){
        String TAG = "avoidrepeat";
        Log.d(TAG, "topic size: " + topics.length);
        NewsArticle[] articles = new NewsArticle[topics.length];
        for(int i = 0; i < topics.length; i++){
            String currentTopic = topics[i];
            Log.d(TAG, "current topic2: " + currentTopic);
            int currentIndex = i;
            newsArticleDbService.getAllNewsOfTheDayArticlesByKeyWord(currentTopic).observe(getActivity(), new Observer<List<NewsArticleRoomModel>>() {
                @Override
                public void onChanged(@Nullable List<NewsArticleRoomModel> articlesForKeyWord) {
                    Log.d(TAG, "article obs size: " + articlesForKeyWord.size());
                    if(!articlesForKeyWord.isEmpty()){
                        Log.d(TAG, "article before adding1: " + articlesForKeyWord.get(0).title);
                        articles[currentIndex] = newsArticleDbService.createNewsArticle(articlesForKeyWord.get(0));
                    }
                    else{
                        articles[currentIndex] = new NewsArticle();
                    }

                    boolean ready = true;
                    for(int i = 0; i < articles.length; i++){
                        if(articles[i] == null){
                            ready = false;
                        }
                    }
                    if(ready){
                        addArticles(articles);
                    }
                    newsArticleDbService.getAllNewsOfTheDayArticlesByKeyWord(currentTopic).removeObserver(this);

                }
            });
        }
    }

    private void addArticles(NewsArticle[] articles){
        if(articlesEmpty()){
            String TAG = "avoidrepeat";
            for(int i = 0; i < articles.length; i++){
                Log.d(TAG, "articles entry: " + articles[i]);
            }
            for(int i = 0; i < articles.length; i++){
                if(!articles[i].title.isEmpty()){
                    articlesOfTheDay.add(articles[i]);
                    newsArticleDbService.setAsRead(newsArticleDbService.createNewsArticleRoomModelToUpdate(articles[i]));
                    adapter.notifyDataSetChanged();
                    DimensionService.setListViewHeightBasedOnItems(articleListView, true);
                    setTextArticlesLoaded();
                    handleLoading(false);
                }
            }
        }
    }


    private boolean articlesEmpty(){
        return articlesOfTheDay.size() == 0;
    }

    private void setTextArticlesLoaded(){
        TextView belowHeadline = view.findViewById(R.id.news_of_the_day_info);
        belowHeadline.setText("Today");
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
