package com.example.rapha.swipeprototype2.loading;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.NewsOfTheDayFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.temporaryDataStorage.ArticleDataStorage;
import com.example.rapha.swipeprototype2.temporaryDataStorage.LanguageSelectionDataStorage;

import java.util.List;

public class DailyNewsLoadingService {

    public static final int LOAD_DAILY_NEWS = 1;
    private static final int MAX_LOADING_TIME_MILLS_DAILY = 10000;

    private static MutableLiveData<Boolean> dailyNewsAreLoading = new MutableLiveData<>();

    public static void setLoading(boolean loading){
        dailyNewsAreLoading.setValue(loading);
    }

    public static MutableLiveData<Boolean> getLoading(){
        return dailyNewsAreLoading;
    }

    public static void reactOnLoadArticlesUnsuccessful(NewsOfTheDayFragment newsOfTheDayFragment){
        MainActivity mainActivity = newsOfTheDayFragment.mainActivity;
        Context context = mainActivity.getApplicationContext();
        new Thread(() -> {
            try {
                Thread.sleep(MAX_LOADING_TIME_MILLS_DAILY);
//                if(articlesOfTheDay.isEmpty()){
//                    Toast.makeText(mainActivity.getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
