package com.raphael.rapha.myNews.sharedPreferencesAccess;

import android.content.Context;

public class SwipeTimeService {

    private final static String FIRST_TOPIC_LIKED = "first_topic_liked" + NewsOfTheDayTimeService.version;
    private final static String REDIRECTED_TO_DAILY = "redirected" + NewsOfTheDayTimeService.version;

    public static void setFirstTopicWasLiked(Context context, boolean liked){
        SharedPreferencesService.storeDataDefault(context, liked, FIRST_TOPIC_LIKED);
    }

    public static boolean firstTopicWasLiked(Context context){
        if(!SharedPreferencesService.valueIsSetDefault(context, FIRST_TOPIC_LIKED)){
            return false;
        }
        return SharedPreferencesService.getBooleanDefault(context, FIRST_TOPIC_LIKED);
    }

    public static void setRedirected(Context context, boolean redirected){
        SharedPreferencesService.storeDataDefault(context, redirected, REDIRECTED_TO_DAILY);
    }

    public static boolean alreadyRedirected(Context context){
        if(!SharedPreferencesService.valueIsSetDefault(context, REDIRECTED_TO_DAILY)){
            return false;
        }
        return SharedPreferencesService.getBooleanDefault(context, REDIRECTED_TO_DAILY);
    }

}
