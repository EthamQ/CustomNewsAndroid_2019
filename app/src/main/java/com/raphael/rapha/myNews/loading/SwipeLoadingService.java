package com.raphael.rapha.myNews.loading;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.temporaryDataStorage.ArticleDataStorage;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.temporaryDataStorage.LanguageSelectionDataStorage;

import java.util.LinkedList;
import java.util.List;

public class SwipeLoadingService {

    public static final int CHANGE_LANGUAGE = 0;
    public static final int FIRST_TIME_LOADING = 5;

    private static MutableLiveData<Boolean> languageChangeLoading = new MutableLiveData<>();
    private static MutableLiveData<Boolean> apiRequestLoading = new MutableLiveData<>();
    private static MutableLiveData<Boolean> apiRequestLoadingLoadingScreen = new MutableLiveData<>();
    private static MutableLiveData<Boolean> databaseLoading = new MutableLiveData<>();

    // If the user quickly changes between languages there are several threads open
    // checking success of the action. So every action gets a separate list entry and
    // is removed when it was checked.
    private static List<LoadingJob> languageChangeJobs = new LinkedList<>();

    public static void setLoadingLanguageChange(boolean loading){
        languageChangeLoading.postValue(loading);
        if(loading){
            LoadingService.addNewLanguageLoadingJob(languageChangeJobs);
        } else {
            LoadingService.setLastLanguageLoadingJobSuccessful(languageChangeJobs);
        }
    }

    public static void setLoadingApiRequest(boolean loading){ apiRequestLoading.setValue(loading); }

    public static void setApiRequestLoadingLoadingScreen(boolean loading) {
        apiRequestLoadingLoadingScreen.setValue(loading);
    }

    public static void setLoadingDatabase(boolean loading){ databaseLoading.setValue(loading); }

    public static void resetLoading(){
        setLoadingApiRequest(false);
        setLoadingDatabase(false);
        setLoadingLanguageChange(false);
        setApiRequestLoadingLoadingScreen(false);
    }

    public static MutableLiveData<Boolean> getLoadingLanguageChange(){ return languageChangeLoading; }

    public static MutableLiveData<Boolean> getLoadingApiRequest(){ return apiRequestLoading; }

    public static MutableLiveData<Boolean> getApiRequestLoadingLoadingScreen() {
        return apiRequestLoadingLoadingScreen;
    }

    public static MutableLiveData<Boolean> getLoadingDatabase(){ return databaseLoading; }

    // Right now not in use because user can react by himself now, may be deleted later
    public static void reactOnLanguageChangeUnsuccessful(SwipeFragment swipeFragment){
        MainActivity mainActivity = swipeFragment.mainActivity;
        Context context = mainActivity.getApplicationContext();
//        new Thread(() -> {
//            try {
//                Thread.sleep(40000);
//                if(!LoadingService.getLastLanguageChangeJobSuccess(languageChangeJobs)){
//                    mainActivity.runOnUiThread(() -> {
//                        swipeFragment.abortLanguageChange();
//                    });
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

}
