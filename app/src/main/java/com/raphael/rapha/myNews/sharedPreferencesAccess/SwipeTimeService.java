package com.raphael.rapha.myNews.sharedPreferencesAccess;

import android.app.Activity;
import android.content.Context;

import com.raphael.rapha.myNews.utils.DateService;

import java.util.Date;

public class SwipeTimeService {

    private final static String FIRST_TIME_LOADING = "swipe_first_time" + NewsOfTheDayTimeService.version;

    public static boolean dataIsLoadedTheFirstTime(Context context){
        if(!SharedPreferencesService.valueIsSetDefault(context, FIRST_TIME_LOADING)){
            return true;
        }
        return SharedPreferencesService.getBooleanDefault(context, FIRST_TIME_LOADING);
    }

    public static void setDataIsLoadedTheFirstTime(Context context, boolean loaded){
        SharedPreferencesService.storeDataDefault(context, loaded, FIRST_TIME_LOADING);
    }

}
