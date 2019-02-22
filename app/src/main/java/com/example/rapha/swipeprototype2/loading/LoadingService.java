package com.example.rapha.swipeprototype2.loading;

import java.util.List;

public class LoadingService {

    public static final int MAX_LOADING_TIME_MILLS_DEFAULT = 7000;

    public static String getLoadingText(int loadingType){
        switch(loadingType){
            case SwipeLoadingService.CHANGE_LANGUAGE: return "Changing language...";
            case DailyNewsLoadingService.LOAD_DAILY_NEWS: return "Loading your daily news...";
            default: return "Loading articles, please wait a moment...";
        }
    }


    public static void addNewLanguageLoadingJob(List<LoadingJob> loadingJobs){
        loadingJobs.add(new LoadingJob());
    }

    public static void setLastLanguageLoadingJobSuccessful(List<LoadingJob> loadingJobs){
        if(!loadingJobs.isEmpty()){
            loadingJobs.get(loadingJobs.size() - 1).finishedSuccessful = true;
        }
    }

    public static boolean getLastLanguageChangeJobSuccess(List<LoadingJob> loadingJobs){
        boolean success = true;
        if(!loadingJobs.isEmpty()){
            success = loadingJobs.get(0).finishedSuccessful;
            loadingJobs.remove(0);
        }
        return success;
    }
}
