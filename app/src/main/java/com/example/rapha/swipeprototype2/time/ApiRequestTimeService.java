package com.example.rapha.swipeprototype2.time;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.swipeprototype2.utils.DateUtils;
import java.util.Date;

public class ApiRequestTimeService {

    private static String version = "8";
    public static String TIME_OF_RELAOD_SWIPE = "time_reload_swipe";
    public static String TIME_OF_RELAOD_DAILY = "time_reload_daily" + version;
    private static int INTERVALL_HOURS_RELAOD_SWIPE = 12;
    private static int INTERVALL_HOURS_RELAOD_DAILY = 24;


    public static boolean forceApiReloadSwipe(Activity activity){
        return DateUtils.hoursBetween(getLastLoaded(activity, TIME_OF_RELAOD_SWIPE), new Date()) > INTERVALL_HOURS_RELAOD_SWIPE;
    }

    public static boolean forceApiReloadDaily(Activity activity){
        return DateUtils.hoursBetween(getLastLoaded(activity, TIME_OF_RELAOD_DAILY), new Date()) > INTERVALL_HOURS_RELAOD_DAILY ;
    }

    public static void saveLastLoaded(Activity activity, Date date, String key){
        if(!(activity == null)){
            SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, DateUtils.dateToLong(date));
            editor.commit();
        }
    }

    public static Date getLastLoaded(Activity activity, String key){
        if(!(activity == null)) {
            SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            long defaultLoad = DateUtils.dateToLong(new Date());
            long dateMills = sharedPreferences.getLong(key, defaultLoad);
            return DateUtils.longToDate(dateMills);
        } else return new Date();
    }

    public static boolean valueIsSet(Activity activity, String key){
        if(!(activity == null)) {
            SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            long value = sharedPreferences.getLong(key, -1);
            return value != -1;
        }
        else return true;
    }
}
