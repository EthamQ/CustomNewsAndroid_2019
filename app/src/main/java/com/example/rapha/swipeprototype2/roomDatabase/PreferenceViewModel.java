package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PreferenceViewModel extends AndroidViewModel {

    private UserPreferenceRepository repository;
    private LiveData<List<UserPreferenceTable>> allCategories;

    public PreferenceViewModel(@NonNull Application application){
        super(application);
        repository = new UserPreferenceRepository(application);
        allCategories = repository.getAllUserPreferences();
    }

    public void insert(UserPreferenceTable userPreferenceTable){
        repository.insert(userPreferenceTable);
    }

    public void update(UserPreferenceTable userPreferenceTable){
        //
    }

    public LiveData<List<UserPreferenceTable>> getAllPreferences(){
        return allCategories;
    }
}
