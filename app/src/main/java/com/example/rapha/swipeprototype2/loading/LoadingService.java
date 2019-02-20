package com.example.rapha.swipeprototype2.loading;

public class LoadingService {

    public static final int MAX_LOADING_TIME_MILLS = 7000;

    public static String getLoadingText(int loadingType){
        switch(loadingType){
            case SwipeLoadingService.CHANGE_LANGUAGE: return "Changing language...";
            case DailyNewsLoadingService.LOAD_DAILY_NEWS: return "Loading your daily news...";
            default: return "Loading articles, please wait a moment...";
        }
    }
}