package com.example.rapha.swipeprototype2.sharedPreferencesAccess;

import android.content.Context;

public class IntroductionAccessService {

    private final static String SHOW_INTRODUCTION = "show_introduction";

    public static boolean getIntroductionShouldBeShown(Context context){
        return SharedPreferencesService.getBooleanDefault(context, SHOW_INTRODUCTION);
    }

    public static void setIntroductionShouldBeShown(Context context, boolean shouldBeShown){
        SharedPreferencesService.storeDataDefault(context, shouldBeShown, SHOW_INTRODUCTION);
    }
}
