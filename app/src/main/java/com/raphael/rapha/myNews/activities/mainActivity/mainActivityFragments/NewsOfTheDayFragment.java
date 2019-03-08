package com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.LiveData;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.arch.lifecycle.Observer;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.activities.viewElements.DimensionService;
import com.raphael.rapha.myNews.customAdapters.NewsOfTheDayListAdapter;
import com.raphael.rapha.myNews.generalServices.CollectionService;
import com.raphael.rapha.myNews.jobScheduler.NewsOfTheDayJobScheduler;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.loading.DailyNewsLoadingService;
import com.raphael.rapha.myNews.roomDatabase.TopicDbService;
import com.raphael.rapha.myNews.roomDatabase.NewsArticleDbService;
import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.sharedPreferencesAccess.NewsOfTheDayTimeService;
import com.raphael.rapha.myNews.generalServices.DateService;

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

    public MainActivity mainActivity;
    public static final int ARTICLE_MINIMUM = 5;
    View view;
    ArrayList<NewsArticle> articlesOfTheDay = new ArrayList();
    ListView articleListView;
    NewsOfTheDayListAdapter adapter;
    NewsArticleDbService newsArticleDbService;
    TopicDbService keyWordDbService;
    boolean articlesAreReady = false;

    // Stored here to remove the subscription when closing the fragment to a void a bug.
    LiveData<List<TopicRoomModel>> likedTopicsLiveData;
    Observer topicObserver;


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
        initObjectsAndServices();
        observeLoadingStatus();
        loadArticles();
        return view;
    }

    private void initObjectsAndServices(){
        mainActivity = (MainActivity) getActivity();
        newsArticleDbService = NewsArticleDbService.getInstance(getActivity().getApplication());
        keyWordDbService = TopicDbService.getInstance(getActivity().getApplication());
        articleListView = view.findViewById(R.id.articleList);
        adapter = new NewsOfTheDayListAdapter(getActivity(), R.layout.news_of_the_day_list_item, articlesOfTheDay, true);
        articleListView.setAdapter(adapter);
        articleListView.setOnItemClickListener((arg0, view, position, arg3) -> {
            NewsArticle clickedArticle = (NewsArticle)articleListView.getItemAtPosition(position);
            clickedArticle.onClick(getActivity());
        });

        // Just for test purposes, remove later
        Button debug = view.findViewById(R.id.debug_button);
        debug.setOnClickListener(view -> {
            scheduleArticleRequests(); DailyNewsLoadingService.setLoading(true);});
    }

    /**
     * Observes if the fragment is currently loading data
     * and shows a loading icon or reloads the fragment after data was loaded.
     */
    private void observeLoadingStatus(){
        // Array because has to be final.
        final boolean[] lastLoadingStatus = new boolean[1];
        DailyNewsLoadingService.getLoading().observe(getActivity(), loading ->{
            handleLoading(loading);
            articlesAreReady = !loading;
            // Reload if change from loading true to false.
            reloadFragmentAfterLoadingData(lastLoadingStatus[0], loading);
            lastLoadingStatus[0] = loading;
        });
    }

    /**
     * Reload the fragment if it previously loaded data and has finished loading.
     * Sometimes needed to display the correct current data when it changed.
     * @param lastLoadingValue
     * @param currentLoadingValue
     */
    private void reloadFragmentAfterLoadingData(boolean lastLoadingValue, boolean currentLoadingValue){
        boolean isLoading = currentLoadingValue;
        boolean wasLoading = lastLoadingValue != currentLoadingValue;
        if(wasLoading && !isLoading){
            reloadFragment();
        }
    }

    private void reloadFragment(){
        if(mainActivity != null){
            mainActivity.loadFragment(R.id.nav_news);
        }
    }

    /**
     * Show a loading icon while new articles are loaded.
     * @param isLoading
     */
    private void handleLoading(boolean isLoading){
        int visibilityLoadingGif = isLoading ? FrameLayout.VISIBLE : FrameLayout.INVISIBLE;
        GifImageView loadingGif = view.findViewById(R.id.news_of_the_day_loading);
        loadingGif.setVisibility(visibilityLoadingGif);

        TextView loadingText = view.findViewById(R.id.news_of_the_day_loading_text);
        loadingText.setVisibility(visibilityLoadingGif);
    }

    /**
     * If the fragment hasn't loaded data before initialize and start the
     * request scheduler which loads articles and stores them in the database
     * in a certain intervall.
     * If data has been loaded before retrieve it from the database.
     */
    private void loadArticles(){
        // If once successfully loaded data first time loading will be false.
        boolean firstTimeLoading = NewsOfTheDayTimeService.firstTimeLoadingData(getContext());
        // Set false at first. If necessary the job scheduler sets it true afterwards.
        DailyNewsLoadingService.setLoading(false);
        if(firstTimeLoading){
            setTextNotEnoughTopics();
            // Only start when enough topics are in the database.
            likedTopicsLiveData = keyWordDbService.getAllLikedKeyWords();
            likedTopicsLiveData.observe(mainActivity, new Observer<List<TopicRoomModel>>() {
                @Override
                public void onChanged(@Nullable List<TopicRoomModel> keyWordRoomModels) {
                    topicObserver = this;
                    if(keyWordRoomModels.size() >= ARTICLE_MINIMUM){
                        scheduleArticleRequests();
                        likedTopicsLiveData.removeObserver(this);
                    }
                }
            });
        }
        else{
            loadArticlesFromDatabase();
        }
    }

    /**
     * Get topics marked as topic of the day from the database, get corresponding articles
     * for every topic, add one article for every topic to the list view.
     */
    private void loadArticlesFromDatabase(){
        // Get topics marked as topic of the day.
        LiveData<List<TopicRoomModel>> topicsOfTheDayLiveData = keyWordDbService.getAllKeyWordsArticlesOfTheDay();
        topicsOfTheDayLiveData.observe(getActivity(), new Observer<List<TopicRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<TopicRoomModel> topics) {
                boolean enoughTopics = topics.size() >= ARTICLE_MINIMUM;
                if(enoughTopics){
                    ConstraintLayout cl = view.findViewById(R.id.empty_text_container);
                    cl.setVisibility(ConstraintLayout.GONE);
                }
                if(!enoughTopics){
                    setTextNotEnoughTopics();
                }
                else if(!topics.isEmpty() && articlesAreReady){
                    int[] languageIds = LanguageSettingsService.generateLanguageDistributionNewsOfTheDay(
                            topics.size(), LanguageSettingsService.loadCheckedLoadedNewsOfTheDay(mainActivity)
                    );
                    for(int i = 0; i < languageIds.length; i++){
                        addArticleForTopicToView(
                                topics.get(i).keyWord,
                                LanguageSettingsService.getLanguageIdAsString(languageIds[i])
                        );
                    }
                    for(int i = 0; i < languageIds.length; i++){
                        addArticleForTopicToView(
                                topics.get(i).keyWord,
                                LanguageSettingsService.getLanguageIdAsString(languageIds[(i + 1) % languageIds.length])
                        );
                    }





//                    boolean[] languages = LanguageSettingsService.loadCheckedLoadedNewsOfTheDay(mainActivity);
//                    for(int i = 0; i < topics.size(); i++){
//                        for(int j = 0; j < languages.length; j++){
//                            if(languages[j]){
//                                addArticleForTopicToView(
//                                        topics.get(i).keyWord,
//                                        LanguageSettingsService.getLanguageIdAsString(j)
//                                );
//                            }
//                        }
//                    }
                    topicsOfTheDayLiveData.removeObserver(this);
                }
            }
        });
    }

    /**
     * Get all articles from the database that have been found with the corresponding topic
     * and aren't archived.
     * Add the first entry to the view.
     * Articles that have once been added to the view are set as read.
     * The scheduler will mark read articles as archived whe loading new articles.
     * @param topic
     */
    private void addArticleForTopicToView(String topic, String languageId){
        if(mainActivity != null){
            LiveData<List<NewsArticleRoomModel>> articlesForTopicLiveData =
                    newsArticleDbService.getAllNewsOfTheDayArticlesByKeyWord(topic);
            articlesForTopicLiveData.observe(mainActivity, new Observer<List<NewsArticleRoomModel>>() {
                @Override
                public void onChanged(@Nullable List<NewsArticleRoomModel> articlesForTopic) {
                    if(!articlesForTopic.isEmpty()) {
                        // Look if you find article with the language Id
                        for(int i = 0; i < articlesForTopic.size(); i++){
                            if(articlesForTopic.get(i).languageId.equals(languageId)){
                                NewsArticleRoomModel modelToAdd = articlesForTopic.get(i);
                                NewsArticle articleToAdd = newsArticleDbService.createNewsArticle(modelToAdd);
                                if(!topicExists(articleToAdd)){
                                    if(!modelToAdd.hasBeenRead){
                                        newsArticleDbService.setAsRead(modelToAdd);
                                    }
                                    articlesOfTheDay.add(articleToAdd);
                                    adapter.notifyDataSetChanged();
                                    DimensionService.setListViewHeightBasedOnItems(articleListView, true);
                                    setTextArticlesLoaded();
                                    articlesForTopicLiveData.removeObserver(this);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Creates a job scheduler which immediately requests articles of the day
     * from the api. Once initialized it will periodically repeat this request.
     */
    private void scheduleArticleRequests(){
        ComponentName componentName = new ComponentName(this.getActivity(), NewsOfTheDayJobScheduler.class);
        JobInfo info = new JobInfo.Builder(NewsOfTheDayTimeService.SCHEDULER_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(NewsOfTheDayTimeService.getRequestIntervallMills())
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode < 0){
            DailyNewsLoadingService.setLoading(false);
        }
    }

    /**
     * If articles are successfully loaded display the date when the articles were loaded.
     */
    private void setTextArticlesLoaded(){
        TextView belowHeadline = view.findViewById(R.id.news_of_the_day_info);
        belowHeadline.setText("Last update ");

        TextView belowHeadlineDate = view.findViewById(R.id.news_of_the_day_date);
        Date lastReload = NewsOfTheDayTimeService.getDateLastLoadedArticles(getContext());
        belowHeadlineDate.setText(DateService.makeDateReadable(lastReload) + "\nUpdated once every 12 hours");
    }

    /**
     * If the user hasn't liked enough topics yet display a text informing him
     * that he has to like topics (answer questions with yes) first.
     */
    private void setTextNotEnoughTopics(){
        //TextView belowHeadline = view.findViewById(R.id.news_of_the_day_date);
        //belowHeadline.setText(R.string.not_enough_topics_news_of_the_day);
        ConstraintLayout cl = view.findViewById(R.id.empty_text_container);
        cl.setVisibility(ConstraintLayout.VISIBLE);

    }

    private boolean topicExists(NewsArticle article){
        return CollectionService.containsTopicNewsArticle(articlesOfTheDay, article.foundWithKeyWord);
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
        if(this.likedTopicsLiveData != null){
            this.likedTopicsLiveData.removeObserver(topicObserver);
        }
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
