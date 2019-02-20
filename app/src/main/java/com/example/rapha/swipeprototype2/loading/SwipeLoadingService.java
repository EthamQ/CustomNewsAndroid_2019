package com.example.rapha.swipeprototype2.loading;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.temporaryDataStorage.ArticleDataStorage;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.temporaryDataStorage.LanguageSelectionDataStorage;

public class SwipeLoadingService {

    public static final int CHANGE_LANGUAGE = 0;

    private static MutableLiveData<Boolean> languageChangeLoading = new MutableLiveData<>();
    private static MutableLiveData<Boolean> apiRequestLoading = new MutableLiveData<>();
    private static MutableLiveData<Boolean> databaseLoading = new MutableLiveData<>();
    private static boolean languageChangeSuccessful;

    public static void setLoadingLanguageChange(boolean loading){
        languageChangeLoading.postValue(loading);
        languageChangeSuccessful = false;
    }

    public static void setLoadingApiRequest(boolean loading){ apiRequestLoading.setValue(loading); }

    public static void setLoadingDatabase(boolean loading){ databaseLoading.setValue(loading); }

    public static void resetLoading(){
        setLoadingLanguageChange(false);
        setLoadingApiRequest(false);
        setLoadingDatabase(false);
        languageChangeSuccessful = true;
    }

    public static MutableLiveData<Boolean> getLoadingLanguageChange(){ return languageChangeLoading; }

    public static MutableLiveData<Boolean> getLoadingApiRequest(){ return apiRequestLoading; }

    public static MutableLiveData<Boolean> getLoadingDatabase(){ return databaseLoading; }

    public static void reactOnLanguageChangeUnsuccessful(SwipeFragment swipeFragment){
        MainActivity mainActivity = swipeFragment.mainActivity;
        Context context = mainActivity.getApplicationContext();
        new Thread(() -> {
            try {
                Thread.sleep(LoadingService.MAX_LOADING_TIME_MILLS_DEFAULT);
                if(swipeFragment.languageChangeIsLoading && !languageChangeSuccessful){
                    mainActivity.runOnUiThread(() -> {
                        SwipeLoadingService.setLoadingLanguageChange(false);
                        swipeFragment.swipeCardsList.addAll(ArticleDataStorage.getBackUpArticlesIfError());
                        swipeFragment.swipeCardArrayAdapter.notifyDataSetChanged();
                        LanguageSettingsService.saveChecked(
                                mainActivity,
                                LanguageSelectionDataStorage.restorePreviousLanguageSelection()
                        );

                        CharSequence text = "Sorry something went wrong, please try it again later or check your internet connection";
                        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                        swipeFragment.reloadFragment();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
