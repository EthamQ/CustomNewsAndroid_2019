package com.example.rapha.swipeprototype2.sharedPreferencesAccess;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.rapha.swipeprototype2.utils.DateUtils;

import java.util.Date;

public class SharedPreferencesService {

    public static void storeData(Activity activity, Date date, String key){
        if(!(activity == null)){
            SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, DateUtils.dateToLong(date));
            editor.commit();
        }
    }

    public static void storeDataDefault(Context context, Date date, String key){
        if(!(context == null)){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, DateUtils.dateToLong(date));
            editor.commit();
        }
    }

    public static void storeDataDefault(Context context, boolean bool, String key){
        if(!(context == null)){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, bool);
            editor.commit();
        }
    }

    public static Date getData(Activity activity, String key){
        if(!(activity == null)) {
            SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            long defaultLoad = DateUtils.dateToLong(new Date());
            long dateMills = sharedPreferences.getLong(key, defaultLoad);
            return DateUtils.longToDate(dateMills);
        } else return new Date();
    }

    public static boolean getBooleanDefault(Context context, String key){
        if(!(context == null)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getBoolean(key, false);
        } else return false;
    }

    public static Date getDateDefault(Context context, String key){
        if(!(context == null)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            long defaultLoad = DateUtils.dateToLong(new Date());
            long dateMills = sharedPreferences.getLong(key, defaultLoad);
            return DateUtils.longToDate(dateMills);
        } else return new Date();
    }

    public static boolean valueIsSet(Activity activity, String key){
        int notSet = -1;
        if(!(activity == null)) {
            SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            long value = sharedPreferences.getLong(key, notSet);
            return value != notSet;
        }
        else return true;
    }

    public static boolean valueIsSetDefault(Context context, String key){
        int notSet = -1;
        if(!(context == null)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            long value = sharedPreferences.getLong(key, notSet);
            return value != notSet;
        }
        else return true;
    }

}
