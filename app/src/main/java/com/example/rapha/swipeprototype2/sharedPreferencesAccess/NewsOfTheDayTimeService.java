package com.example.rapha.swipeprototype2.sharedPreferencesAccess;

import android.content.Context;

import java.util.Date;

public class NewsOfTheDayTimeService {

    private static String version = "36";
    private static String TIME_OF_RELAOD_DAILY = "time_reload_daily" + version;
    private static int INTERVALL_HOURS_RELAOD_DAILY_HOURS = 24;
    private static boolean testing = true;
    public static final int SCHEDULER_ID = 123;

    public static void saveDateLastLoadedArticles(Context context, Date date){
        SharedPreferencesService.storeDataDefault(context, date, TIME_OF_RELAOD_DAILY);
    }

    public static Date getDateLastLoadedArticles(Context context){
        return SharedPreferencesService.getDateDefault(context, TIME_OF_RELAOD_DAILY);
    }

    public static boolean firstTimeLoadingData(Context context){
        return !SharedPreferencesService.valueIsSetDefault(context, TIME_OF_RELAOD_DAILY);
    }


    public static void resetLastLoaded(Context context){
        SharedPreferencesService.deleteDataDefault(context, TIME_OF_RELAOD_DAILY);
    }

    public static int getRequestIntervallMills(){
        int second = 60;
        int minute = 60;
        int millsFactor = 1000;
        if(testing){
            return 60 * second * millsFactor;
        }
        else{
            return INTERVALL_HOURS_RELAOD_DAILY_HOURS * second * minute * millsFactor;
        }
    }
}
