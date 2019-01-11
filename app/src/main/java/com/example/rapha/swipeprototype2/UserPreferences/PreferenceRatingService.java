package com.example.rapha.swipeprototype2.UserPreferences;

import android.util.Log;

public class PreferenceRatingService {
    public static void rateAsInteresting(int newsCategory){
        Log.d("RATE", "rateAsInteresting: "+ newsCategory);
    }

    public static void rateAsNotInteresting(int newsCategory){
        Log.d("RATE", "rateAsNotInteresting: "+ newsCategory);
    }
}
