package com.raphael.rapha.myNews.sharedPreferencesAccess;

import android.content.Context;

public class InAppPaymentService {

    public static final String MONTHLY_PAYMENT_SKU = "test_monthly";

    public static void setUserSubscribed(Context context, boolean subscribed){
        SharedPreferencesService.storeDataDefault(context, subscribed, MONTHLY_PAYMENT_SKU);
    }

    public static boolean userIsSubscribed(Context context){
        if(!SharedPreferencesService.valueIsSetDefault(context, MONTHLY_PAYMENT_SKU)){
            return false;
        }
        else return SharedPreferencesService.getBooleanDefault(context, MONTHLY_PAYMENT_SKU);
    }
}
