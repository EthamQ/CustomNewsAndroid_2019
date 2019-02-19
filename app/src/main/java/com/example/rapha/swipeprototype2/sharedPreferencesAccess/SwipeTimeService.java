package com.example.rapha.swipeprototype2.sharedPreferencesAccess;

import android.app.Activity;

import com.example.rapha.swipeprototype2.utils.DateService;

import java.util.Date;

public class SwipeTimeService {

    public static String TIME_OF_RELAOD_SWIPE = "time_reload_swipe";
    private static int INTERVALL_HOURS_RELAOD_SWIPE = 12;

    public static boolean forceApiReloadSwipe(Activity activity){
        return DateService.hoursBetween(getLastLoaded(activity, TIME_OF_RELAOD_SWIPE), new Date()) > INTERVALL_HOURS_RELAOD_SWIPE;
    }

    public static void saveLastLoaded(Activity activity, Date date){
        SharedPreferencesService.storeData(activity, date, TIME_OF_RELAOD_SWIPE);
    }

    public static Date getLastLoaded(Activity activity, String key){
        return SharedPreferencesService.getData(activity, key);
    }
}
