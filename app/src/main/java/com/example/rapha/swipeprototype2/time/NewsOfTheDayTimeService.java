package com.example.rapha.swipeprototype2.time;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.swipeprototype2.sharedPreferences.SharedPreferencesService;
import com.example.rapha.swipeprototype2.utils.DateUtils;
import java.util.Date;

public class NewsOfTheDayTimeService {

    private static String version = "26";
    private static String TIME_OF_RELAOD_DAILY = "time_reload_daily" + version;
    private static int INTERVALL_HOURS_RELAOD_DAILY_HOURS = 24;
    private static boolean testing = true;
    public static final int SCHEDULER_ID = 123;

    public static void saveDateLastLoadedArticles(Context context, Date date){
        SharedPreferencesService.storeDataDefault(context, date, TIME_OF_RELAOD_DAILY);
    }

    public static Date getDateLastLoadedArticles(Context context){
        return SharedPreferencesService.getDataDefault(context, TIME_OF_RELAOD_DAILY);
    }

    public static boolean firstTimeLoadingData(Context context){
        return !SharedPreferencesService.valueIsSetDefault(context, TIME_OF_RELAOD_DAILY);
    }

    public static int getRequestIntervallMills(){
        int second = 60;
        int minute = 60;
        int millsFactor = 1000;
        if(testing){
            return 15 * second * millsFactor;
        }
        else{
            return INTERVALL_HOURS_RELAOD_DAILY_HOURS * second * minute * millsFactor;
        }
    }
}
