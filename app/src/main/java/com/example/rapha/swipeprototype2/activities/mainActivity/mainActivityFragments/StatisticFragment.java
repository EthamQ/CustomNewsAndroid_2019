package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.viewElements.DimensionService;
import com.example.rapha.swipeprototype2.activities.viewElements.StatisticsFragmentDimensions;
import com.example.rapha.swipeprototype2.customAdapters.TopicRowAdapter;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryService;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.RatingDbService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static int ROW_LENGTH = 3;

    View view;
    StatisticsFragmentDimensions statisticsFragmentDimensions;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_statistic, container, false);
        statisticsFragmentDimensions = new StatisticsFragmentDimensions(this);
        setFirstGraphView();
        setSecondGraphView();
        setLikedTopics();
        return view;
    }

    /**
     * A row of topics in the statistic fragment has 3 columns.
     * So create a list of String arrays which have all a length of 3
     * and contain 3 of the topics.
     * It will then be passed to the adapter for the list view.
     */
    public void setLikedTopics(){
        ArrayList<String[]> topicSets = new ArrayList<>();
        ListView listView = view.findViewById(R.id.list);
        TopicRowAdapter topicRowAdapter = new TopicRowAdapter(
                getActivity().getApplicationContext(),
                R.id.list,
                topicSets);
        listView.setAdapter(topicRowAdapter);
        topicRowAdapter.notifyDataSetChanged();

        KeyWordDbService keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
        keyWordDbService.getAllLikedKeyWords().observe(getActivity(), likedKeyWords -> {
            topicSets.clear();
                String[] topicSet = new String[]{"", "", ""};
                boolean firstSetFull = !(likedKeyWords.size() < ROW_LENGTH);
                if(!firstSetFull){
                    for(int i = 0; i < likedKeyWords.size(); i++){
                        topicSet[i] = likedKeyWords.get(i).keyWord;
                    }
                    topicSets.add(topicSet);
                    topicRowAdapter.notifyDataSetChanged();
                } else{
                    for(int i = 0; i < likedKeyWords.size(); i++){
                        topicSet[i % ROW_LENGTH] = likedKeyWords.get(i).keyWord;
                        boolean endOfCurrentSet = (i + 1) % ROW_LENGTH  == 0;
                        if(endOfCurrentSet){
                            topicSets.add(topicSet);
                            topicSet = new String[]{"", "", ""};
                        }
                        boolean isLastIteration = i == likedKeyWords.size() - 1;
                        boolean lastSetFull = likedKeyWords.size() % ROW_LENGTH == 0;
                        if(isLastIteration && !lastSetFull){
                            topicSets.add(topicSet);
                        }
                    }
                }
                topicRowAdapter.notifyDataSetChanged();
                DimensionService.setListViewHeightBasedOnItems(listView, false);
                if(topicSets.size() > 0){
                    TextView likedTopics = view.findViewById(R.id.liked_topics);
                    likedTopics.setText("");
                }
        });
    }

    public void setFirstGraphView(){
        GraphView graphView = view.findViewById(R.id.graph1);
        RatingDbService dbService = RatingDbService.getInstance(getActivity().getApplication());
        dbService.getAllUserPreferences().observe(getActivity(), newsCategories -> {
            DataPoint[] datapoints = new DataPoint[5];
            String[] xAxisName = new String[5];

            for(UserPreferenceRoomModel category : newsCategories){
                if(category.getNewsCategoryId() == NewsCategoryContainer.Finance.CATEGORY_ID){
                    datapoints[0] = new DataPoint(0, category.getRating());
                    xAxisName[0] = NewsCategoryService.getDisplayNameForCategory(category.getNewsCategoryId());
                }
                if(category.getNewsCategoryId() == NewsCategoryContainer.Politics.CATEGORY_ID){
                    datapoints[1] = new DataPoint(1, category.getRating());
                    xAxisName[1] = NewsCategoryService.getDisplayNameForCategory(category.getNewsCategoryId());
                }
                if(category.getNewsCategoryId() == NewsCategoryContainer.Food.CATEGORY_ID){
                    datapoints[2] = new DataPoint(2, category.getRating());
                    xAxisName[2] = NewsCategoryService.getDisplayNameForCategory(category.getNewsCategoryId());
                }
                if(category.getNewsCategoryId() == NewsCategoryContainer.Technology.CATEGORY_ID){
                    datapoints[3] = new DataPoint(3, category.getRating());
                    xAxisName[3] = NewsCategoryService.getDisplayNameForCategory(category.getNewsCategoryId());
                }
                if(category.getNewsCategoryId() == NewsCategoryContainer.Movie.CATEGORY_ID){
                    datapoints[4] = new DataPoint(4, category.getRating());
                    xAxisName[4] = NewsCategoryService.getDisplayNameForCategory(category.getNewsCategoryId());
                }
            }
            setGraph(graphView, datapoints, xAxisName, true);
        });
    }

    public void setSecondGraphView(){
        GraphView graphView = view.findViewById(R.id.graph2);
        RatingDbService dbService = RatingDbService.getInstance(getActivity().getApplication());
        dbService.getAllUserPreferences().observe(getActivity(), newsCategories -> {
            DataPoint[] datapoints = new DataPoint[5];
            String[] xAxisName = new String[5];

            for(UserPreferenceRoomModel category : newsCategories){
                if(category.getNewsCategoryId() == NewsCategoryContainer.Sport.CATEGORY_ID){
                    datapoints[0] = new DataPoint(0, category.getRating());
                    xAxisName[0] = NewsCategoryService.getDisplayNameForCategory(category.getNewsCategoryId());
                }
                if(category.getNewsCategoryId() == NewsCategoryContainer.Health.CATEGORY_ID){
                    datapoints[1] = new DataPoint(1, category.getRating());
                    xAxisName[1] = NewsCategoryService.getDisplayNameForCategory(category.getNewsCategoryId());
                }
            }
            datapoints[2] = new DataPoint(2, 0);
            xAxisName[2] = "";
            datapoints[3] = new DataPoint(3, 0);
            xAxisName[3] = "";
            datapoints[4] = new DataPoint(4, 0);
            xAxisName[4] = "";
            setGraph(graphView, datapoints, xAxisName, false);
        });
    }

    /**
     *  Set the values on the x and y axis of the graph and add styling.
     *  x: Name of the category, y: empty
     *  Also set the height of the individual graphs for every category depending
     *  on the category rating.
     */
    public void setGraph(GraphView graphView, DataPoint[] datapoints, String[] xAxisNames, boolean title){
        // Get user preferences from database.

            statisticsFragmentDimensions.setGraphWidth(graphView);

                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(datapoints);

                // Additional graph styling.
                series.setSpacing(10);
                series.setDataWidth(1);
                if(title){
                    graphView.setTitle("Your news preferences");
                }
                graphView.addSeries(series);

                // Convert the x axis values to strings representing its news category.
                graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            return xAxisNames[(int) value];
                        }
                        else {
                            return "";
                        }
                    }
                });

                // styling
                series.setValueDependentColor(data -> ContextCompat.getColor(getContext(), R.color.news_card_text));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
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
