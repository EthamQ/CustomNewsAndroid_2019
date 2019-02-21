package com.example.rapha.swipeprototype2.loading;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.temporaryDataStorage.ArticleDataStorage;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.temporaryDataStorage.LanguageSelectionDataStorage;

import java.util.LinkedList;
import java.util.List;

public class SwipeLoadingService {

    public static final int CHANGE_LANGUAGE = 0;

    private static MutableLiveData<Boolean> languageChangeLoading = new MutableLiveData<>();
    private static MutableLiveData<Boolean> apiRequestLoading = new MutableLiveData<>();
    private static MutableLiveData<Boolean> databaseLoading = new MutableLiveData<>();

    // If the user quickly changes between languages there are several threads open
    // checking success of the action. So every action gets a separate list entry and
    // is removed when it was checked.
    private static List<LoadingJob> languageChangeJobs = new LinkedList<>();

    public static void setLoadingLanguageChange(boolean loading){
        languageChangeLoading.postValue(loading);
        if(loading){
            addNewLanguageLoadingJob();
        } else {
            setLastLanguageLoadingJobSuccessful();
        }
    }

    public static void setLoadingApiRequest(boolean loading){ apiRequestLoading.setValue(loading); }

    public static void setLoadingDatabase(boolean loading){ databaseLoading.setValue(loading); }

    public static void resetLoading(){
        setLoadingApiRequest(false);
        setLoadingDatabase(false);
        setLoadingLanguageChange(false);
    }

    public static MutableLiveData<Boolean> getLoadingLanguageChange(){ return languageChangeLoading; }

    public static MutableLiveData<Boolean> getLoadingApiRequest(){ return apiRequestLoading; }

    public static MutableLiveData<Boolean> getLoadingDatabase(){ return databaseLoading; }

    public static void reactOnLanguageChangeUnsuccessful(SwipeFragment swipeFragment){
        MainActivity mainActivity = swipeFragment.mainActivity;
        Context context = mainActivity.getApplicationContext();
        new Thread(() -> {
            try {
                Thread.sleep(12000);
                if(!getLastLanguageChangeJobSuccess()){
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

    private static boolean getLastLanguageChangeJobSuccess(){
        boolean success = true;
        if(!languageChangeJobs.isEmpty()){
            success = languageChangeJobs.get(0).finishedSuccessful;
            languageChangeJobs.remove(0);
        }
        return success;
    }

    private static void addNewLanguageLoadingJob(){
        languageChangeJobs.add(new LoadingJob());
    }

    private static void setLastLanguageLoadingJobSuccessful(){
        if(!languageChangeJobs.isEmpty()){
            languageChangeJobs.get(languageChangeJobs.size() - 1).finishedSuccessful = true;
        }
    }
}
