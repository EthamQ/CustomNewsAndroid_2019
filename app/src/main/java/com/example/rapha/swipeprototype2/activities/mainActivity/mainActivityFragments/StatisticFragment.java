package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.RatingDbService;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.List;

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

    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatisticFragment() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_statistic, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Your Statistics");
        setGraph();
        setLikedTopics();
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

    public void setLikedTopics(){
        KeyWordDbService keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
        keyWordDbService.getAllLikedKeyWords().observe(getActivity(), new Observer<List<KeyWordRoomModel>>(){
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> likedKeyWords) {
                TextView likedTopics = view.findViewById(R.id.liked_topics);
                String newText = "";
                for(int i = 0; i < likedKeyWords.size(); i++){
                        newText += likedKeyWords.get(i).keyWord;
                        if(i > 0 || i < (likedKeyWords.size() - 1)){
                            newText += ", ";
                        }
                }
                if(!newText.isEmpty()){
                    likedTopics.setText(newText);
                }
            }
        });
    }

    public void setGraph(){
        // Get user preferences from database.
        RatingDbService dbService = RatingDbService.getInstance(getActivity().getApplication());
        dbService.getAllUserPreferences().observe(getActivity(), new Observer<List<UserPreferenceRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<UserPreferenceRoomModel> userPreferenceRoomModels) {
                // Set the values in the graph.
                GraphView graph = view.findViewById(R.id.graph);
                DataPoint[] datapoints = new DataPoint[userPreferenceRoomModels.size()];
                for(int i = 0; i < datapoints.length; i++){
                    UserPreferenceRoomModel currentEntry = userPreferenceRoomModels.get(i);
                    datapoints[i] = new DataPoint(currentEntry.getNewsCategoryId(), currentEntry.getRating());
                }
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(datapoints);

                // Additional graph styling.
                series.setSpacing(10);
                graph.setTitle("Your news preferences");
//                graph.setTitleColor(Color.BLACK);
                graph.addSeries(series);
//                graph.getGridLabelRenderer().setVerticalLabelsColor(ContextCompat.getColor(getContext(), R.color.news_card_text));
//                graph.getGridLabelRenderer().setHorizontalLabelsColor(ContextCompat.getColor(getContext(), R.color.news_card_text));
//                graph.getGridLabelRenderer().setVerticalLabelsColor(ContextCompat.getColor(getContext(), R.color.news_card_text));
//                graph.getGridLabelRenderer().setHorizontalLabelsColor(ContextCompat.getColor(getContext(), R.color.news_card_text));
//                graph.getGridLabelRenderer().reloadStyles();

                // Convert the x axis values to strings representing its news category.
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            final NewsCategoryContainer categories = new NewsCategoryContainer();
                            // TODO: move this logic and store the strings somewhere else!
                            if(value == categories.finance.getCategoryID()){
                                return "Finance";
                            }
                            if(value == categories.politics.getCategoryID()){
                                return "Politics";
                            }
                            if(value == categories.food.getCategoryID()){
                                return "Food";
                            }
                            if(value == categories.technology.getCategoryID()){
                                return "Technology";
                            }
                            if(value == categories.movie.getCategoryID()){
                                return "Movies";
                            }
                            return super.formatLabel(value, isValueX);
                        } else {
                            return "";
                        }
                    }
                });

                // styling
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return ContextCompat.getColor(getContext(), R.color.turquis_dark);
                    }
                });
            }
        });
    }
}
