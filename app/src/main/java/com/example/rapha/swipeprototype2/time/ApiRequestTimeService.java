package com.example.rapha.swipeprototype2.time;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.rapha.swipeprototype2.utils.DateUtils;
import java.util.Date;

public class ApiRequestTimeService {

    private static String TIME_OF_RELAOD = "time_reload";
    private static int INTERVALL_HOURS_RELAOD = 12;


    public static boolean forceApiReload(AppCompatActivity activity){
        return DateUtils.hoursBetween(getLastLoaded(activity), new Date()) > INTERVALL_HOURS_RELAOD;
    }

    public static void saveLastLoaded(AppCompatActivity activity, Date date){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(TIME_OF_RELAOD, DateUtils.dateToLong(date));
        editor.commit();
    }

    private static Date getLastLoaded(AppCompatActivity activity){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        long defaultLoad = DateUtils.dateToLong(new Date());
        long dateMills = sharedPreferences.getLong(TIME_OF_RELAOD, defaultLoad);
        return DateUtils.longToDate(dateMills);
    }
}
