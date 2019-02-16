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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.arch.lifecycle.Observer;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.viewElements.DimensionService;
import com.example.rapha.swipeprototype2.customAdapters.NewsOfTheDayListAdapter;
import com.example.rapha.swipeprototype2.jobScheduler.NewsOfTheDayJobScheduler;
import com.example.rapha.swipeprototype2.loading.DailyNewsLoadingService;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.sharedPreferencesAccess.NewsOfTheDayTimeService;
import com.example.rapha.swipeprototype2.utils.CollectionService;
import com.example.rapha.swipeprototype2.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
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
        boolean[] last = new boolean[1];
        DailyNewsLoadingService.getLoading().observe(getActivity(), loading ->{
                handleLoading(loading);
                reloadFragmentAfterLoadingData(last[0], loading);
                last[0] = loading;
                });
        return view;
    }

    private void reloadFragmentAfterLoadingData(boolean lastLoadingValue, boolean currentLoadingValue){
        if(!(getActivity() == null) && lastLoadingValue != currentLoadingValue && !currentLoadingValue){
            if(!(NewsOfTheDayFragment.this.getActivity() == null)){
                ((MainActivity) NewsOfTheDayFragment.this.getActivity()).changeFragmentTo(R.id.nav_news);
            }
        }
    }

    private void init(){
        newsArticleDbService = NewsArticleDbService.getInstance(getActivity().getApplication());
        keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
        articleListView = view.findViewById(R.id.articleList);
        adapter = new NewsOfTheDayListAdapter(getActivity(), R.layout.news_of_the_day_list_item, articlesOfTheDay);
        articleListView.setAdapter(adapter);
        articleListView.setOnItemClickListener((arg0, view, position, arg3) -> {
                NewsArticle clickedArticle = (NewsArticle)articleListView.getItemAtPosition(position);
                clickedArticle.onClick(getActivity());
        });

        Button debug = view.findViewById(R.id.debug_button);
        debug.setOnClickListener(view -> {initArticleRequestScheduler(); DailyNewsLoadingService.setLoading(true);});
    }

    private void handleLoading(boolean isLoading){
        int visibilityLoadingGif = isLoading ? FrameLayout.VISIBLE : FrameLayout.INVISIBLE;
        GifImageView loadingGif = view.findViewById(R.id.news_of_the_day_loading);
        loadingGif.setVisibility(visibilityLoadingGif);
    }

    private void loadArticles(){
        boolean firstTimeLoading = NewsOfTheDayTimeService.firstTimeLoadingData(getContext());
        if(firstTimeLoading){
            initArticleRequestScheduler();
            setTextNotEnoughTopics();
        }
        else{
            loadArticlesFromDatabase();
        }
    }

    private void initArticleRequestScheduler(){
        ComponentName componentName = new ComponentName(this.getActivity(), NewsOfTheDayJobScheduler.class);
        JobInfo info = new JobInfo.Builder(NewsOfTheDayTimeService.SCHEDULER_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(NewsOfTheDayTimeService.getRequestIntervallMills())
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
    }

    private void loadArticlesFromDatabase(){
        // Start the chain of different database requests.
        getTopicsOfTheDay();
    }

    /**
     * Get all topics that are marked as topics of the day from the database.
     * Pass them to getArticlesForTopics().
     */
    private void getTopicsOfTheDay(){
        keyWordDbService.getAllKeyWordsArticlesOfTheDay().observe(getActivity(), new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> topics) {
                boolean articlesHaveBeenAdded = !articlesOfTheDay.isEmpty();
                boolean enoughTopics = topics.size() >= ARTICLE_MINIMUM;
                if(!enoughTopics){
                    setTextNotEnoughTopics();
                }
                if(!articlesHaveBeenAdded && enoughTopics){
                    String[] topicsTemp = new String[topics.size()];
                    for(int i = 0; i < topics.size(); i++){
                        final String currentTopic = topics.get(i).keyWord;
                        topicsTemp[i] = currentTopic;
                    }
                    getArticlesForTopics(topicsTemp);
                    keyWordDbService.getAllKeyWordsArticlesOfTheDay().removeObserver(this);
                }
            }
        });
    }

    /**
     * Request the articles for every topic from the database and
     * pass them to addArticlesToListView() when done.
     * @param topics
     */
    private void getArticlesForTopics(String[] topics){
        NewsArticle[] articles = new NewsArticle[topics.length];
        for(int i = 0; i < topics.length; i++){
            String currentTopic = topics[i];
            int currentIndex = i;
            newsArticleDbService.getAllNewsOfTheDayArticlesByKeyWord(currentTopic).observe(getActivity(), new Observer<List<NewsArticleRoomModel>>() {
                @Override
                public void onChanged(@Nullable List<NewsArticleRoomModel> articlesForKeyWord) {
                    articles[currentIndex] = articlesForKeyWord.isEmpty()?
                            new NewsArticle() : newsArticleDbService.createNewsArticle(articlesForKeyWord.get(0));
                    if(!CollectionService.arrayHasNullValues(articles)){
                        addArticlesToListView(articles);
                    }
                    newsArticleDbService.getAllNewsOfTheDayArticlesByKeyWord(currentTopic).removeObserver(this);
                }
            });
        }
    }


    /**
     * Add every article to the array that belongs to the list view.
     * Set all articles as hasBeenRead = true in the database.
     * @param articles
     */
    private void addArticlesToListView(NewsArticle[] articles){
        if(articlesOfTheDay.isEmpty()){
            for(int i = 0; i < articles.length; i++){
                if(!articles[i].title.isEmpty()){

                    articlesOfTheDay.add(articles[i]);
                    newsArticleDbService.setAsRead(newsArticleDbService.createNewsArticleRoomModelToUpdate(articles[i]));
                    adapter.notifyDataSetChanged();
                    DimensionService.setListViewHeightBasedOnItems(articleListView, true);
                    setTextArticlesLoaded();
                    Log.d("archived", "Add to view: " + "archived: " + articles[i].archived + ", read: " + articles[i].hasBeenRead +", title: " +  articles[i].title);
                }
            }
        }
    }

    private void setTextArticlesLoaded(){
        TextView belowHeadline = view.findViewById(R.id.news_of_the_day_info);
        belowHeadline.setText("Last update ");

        TextView belowHeadlineDate = view.findViewById(R.id.news_of_the_day_date);
        Date lastReload = NewsOfTheDayTimeService.getDateLastLoadedArticles(getContext());
        belowHeadlineDate.setText(DateUtils.makeDateReadable(lastReload));
    }

    private void setTextNotEnoughTopics(){
        TextView belowHeadline = view.findViewById(R.id.news_of_the_day_info);
        belowHeadline.setText(R.string.not_enough_topics_news_of_the_day);
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
