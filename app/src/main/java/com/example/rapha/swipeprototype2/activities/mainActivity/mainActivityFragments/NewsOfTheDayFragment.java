package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.AnyThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.utils.ListService;

import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsOfTheDayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsOfTheDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsOfTheDayFragment extends Fragment implements IKeyWordProvider{

    List<KeyWordRoomModel> topicsToLookFor;
    LinkedList<NewsArticle> articlesOfTheDay = new LinkedList<>();


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_of_the_day, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
        loadNewsTopics();
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


    @AnyThread
    public void loadNewsTopics(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    KeyWordDbService keyWordDbService = KeyWordDbService.getInstance(getActivity().getApplication());
                    keyWordDbService.getAllLikedKeyWords().observe(getActivity(), new android.arch.lifecycle.Observer<List<KeyWordRoomModel>>() {
                        @Override
                        public void onChanged(@Nullable List<KeyWordRoomModel> keyWordRoomModels) {
                            topicsToLookFor = keyWordRoomModels;
                            topicsToLookFor = ListService.removeAllEntriesStartingAt(topicsToLookFor,10);

                                for(int i = 0; i < 1; i++) {
                                    String[] keyWords = new String[]{topicsToLookFor.get(i).keyWord};
                                    LinkedList<NewsArticle> fetchedArticles = new LinkedList<>();
                                    try {
                                        fetchedArticles = ApiService.getArticlesNewsApiByKeyWords(
                                                NewsOfTheDayFragment.this, keyWords, LanguageSettingsService.INDEX_ENGLISH
                                        );
                                        Log.d("oftheday", "Query resultsize: " + fetchedArticles.size());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("oftheday", "Query error: " + e.toString());
                                        if (android.os.Build.VERSION.SDK_INT > 9) {
                                            StrictMode.ThreadPolicy policy =
                                                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                        }
                                        loadNewsTopics();
                                    }
                                    if (fetchedArticles.size() > 0) {
                                        articlesOfTheDay.add(fetchedArticles.get(0));
                                        Log.d("oftheday2", "add article: " + fetchedArticles.get(0).title);
                                    }
                                }
                        }
                    });
            }
        });
        thread.start();
    }


}
