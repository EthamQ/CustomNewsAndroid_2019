package com.example.rapha.swipeprototype2.roomDatabase.categoryRating;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PreferenceViewModel extends AndroidViewModel {

    private UserPreferenceRepository repository;
    private LiveData<List<UserPreferenceRoomModel>> allCategories;

    public PreferenceViewModel(@NonNull Application application){
        super(application);
        repository = new UserPreferenceRepository(application);
        allCategories = repository.getAllUserPreferences();
    }

    public void insert(UserPreferenceRoomModel userPreferenceRoomModel){
        repository.insert(userPreferenceRoomModel);
    }

    public void update(UserPreferenceRoomModel userPreferenceRoomModel){
        //
    }

    public LiveData<List<UserPreferenceRoomModel>> getAllPreferences(){
        return allCategories;
    }
}
