package com.example.rapha.swipeprototype2.loading;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

public class DailyNewsLoadingService {

    private static MutableLiveData<Boolean> dailyNewsAreLoading = new MutableLiveData<>();

    public static void setLoading(boolean loading){
        dailyNewsAreLoading.setValue(loading);
    }

    public static MutableLiveData<Boolean> getLoading(){
        return dailyNewsAreLoading;
    }
}
