package com.raphael.rapha.myNews.loading;

import android.arch.lifecycle.MutableLiveData;

import com.raphael.rapha.myNews.jobScheduler.NewsOfTheDayJobScheduler;

import java.util.LinkedList;
import java.util.List;

public class DailyNewsLoadingService {

    public static final int LOAD_DAILY_NEWS = 1;
    private static final int MAX_LOADING_TIME_MILLS_DAILY = 10000;

    private static MutableLiveData<Boolean> dailyNewsAreLoading = new MutableLiveData<>();
    private static List<LoadingJob> dailyNewsLoadingJobs = new LinkedList<>();

    public static void setLoading(boolean loading){
        dailyNewsAreLoading.postValue(loading);
        if(loading){
            LoadingService.addNewLanguageLoadingJob(dailyNewsLoadingJobs);
        } else {
            LoadingService.setLastLanguageLoadingJobSuccessful(dailyNewsLoadingJobs);
        }
    }

    public static MutableLiveData<Boolean> getLoading(){
        return dailyNewsAreLoading;
    }

    public static void loadingInterrupted(){
        dailyNewsLoadingJobs.clear();
        setLoading(false);
    }


    public static void reactOnLoadArticlesUnsuccessful(NewsOfTheDayJobScheduler jobScheduler){
        new Thread(() -> {
            try {
                Thread.sleep(MAX_LOADING_TIME_MILLS_DAILY);
                boolean loadingSuccess = LoadingService.getLastLanguageChangeJobSuccess(dailyNewsLoadingJobs);
                if(!loadingSuccess){
                    setLoading(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
