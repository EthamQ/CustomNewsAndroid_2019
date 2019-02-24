package com.raphael.rapha.myNews.sharedPreferencesAccess;

import android.content.Context;

public class SettingsService {

    private static final String NOTIFICATION_SETTING_KEY = "notification_setting";

    public static boolean getCheckedNotification(Context context){
        if(!SharedPreferencesService.valueIsSetDefault(context, NOTIFICATION_SETTING_KEY)){
            return true;
        }
        return SharedPreferencesService.getBooleanDefault(context, NOTIFICATION_SETTING_KEY);
    }

    public static void setCheckedNotification(Context context, boolean checked){
        SharedPreferencesService.storeDataDefault(context, checked, NOTIFICATION_SETTING_KEY);
    }
}
