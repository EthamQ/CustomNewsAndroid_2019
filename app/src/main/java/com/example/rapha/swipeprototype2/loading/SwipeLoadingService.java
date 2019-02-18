package com.example.rapha.swipeprototype2.loading;

import android.arch.lifecycle.MutableLiveData;

public class SwipeLoadingService {

    public static final int CHANGE_LANGUAGE = 0;

    private static MutableLiveData<Boolean> languageChangeLoading = new MutableLiveData<>();
    private static MutableLiveData<Boolean> apiRequestLoading = new MutableLiveData<>();

    public static void setLoadingLanguageChange(boolean loading){ languageChangeLoading.setValue(loading); }

    public static void setLoadingApiRequest(boolean loading){ apiRequestLoading.setValue(loading); }

    public static MutableLiveData<Boolean> getLoadingLanguageChange(){ return languageChangeLoading; }

    public static MutableLiveData<Boolean> getLoadingApiRequest(){ return apiRequestLoading; }
}
