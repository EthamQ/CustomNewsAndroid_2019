package com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.activities.viewElements.DimensionService;
import com.raphael.rapha.myNews.customAdapters.NewsOfTheDayListAdapter;
import com.raphael.rapha.myNews.loading.DailyNewsLoadingService;
import com.raphael.rapha.myNews.roomDatabase.KeyWordDbService;
import com.raphael.rapha.myNews.roomDatabase.NewsArticleDbService;
import com.raphael.rapha.myNews.roomDatabase.NewsHistoryDbService;
import com.raphael.rapha.myNews.roomDatabase.newsHistory.NewsHistoryRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsHistoryFragment extends Fragment {

    public MainActivity mainActivity;
    public static final int ARTICLE_MAXIMUM = 30;
    View view;
    ArrayList<NewsArticle> articlesToDisplay = new ArrayList();
    ListView articleListView;
    NewsOfTheDayListAdapter adapter;
    NewsHistoryDbService newsHistoryDbService;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsHistoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_history, container, false);
        initObjectsAndServices();
        loadArticlesFromDatabase();
        return view;
    }

    private void initObjectsAndServices(){
        mainActivity = (MainActivity) getActivity();
        newsHistoryDbService = NewsHistoryDbService.getInstance(mainActivity.getApplication());
        articleListView = view.findViewById(R.id.article_list_history);
        adapter = new NewsOfTheDayListAdapter(getActivity(), R.layout.fragment_news_history, articlesToDisplay, false);
        articleListView.setAdapter(adapter);
        articleListView.setOnItemClickListener((arg0, view, position, arg3) -> {
            NewsArticle clickedArticle = (NewsArticle)articleListView.getItemAtPosition(position);
            clickedArticle.onClick(getActivity());
        });
    }

    private void loadArticlesFromDatabase(){
        newsHistoryDbService.getAll().observe(mainActivity, articles -> {
            articlesToDisplay.clear();
            ConstraintLayout layout = view.findViewById(R.id.empty_text_container_history);
            if(articles.isEmpty()){
                layout.setVisibility(ConstraintLayout.VISIBLE);
            }
            else{
                layout.setVisibility(ConstraintLayout.GONE);
                int limit = ARTICLE_MAXIMUM > articles.size() ? articles.size() : ARTICLE_MAXIMUM;
                for(int i = 0; i < limit; i++){
                    articlesToDisplay.add(NewsHistoryDbService.toNewsArticle(articles.get(i)));
                    DimensionService.setListViewHeightBasedOnItems(articleListView, false);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsHistoryFragment newInstance(String param1, String param2) {
        NewsHistoryFragment fragment = new NewsHistoryFragment();
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
