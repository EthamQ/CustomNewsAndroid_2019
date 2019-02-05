package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRepository;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.List;

public class RatingDbService {

    private static RatingDbService instance;
    UserPreferenceRepository repository;

    private RatingDbService(Application application){
        this.repository = new UserPreferenceRepository(application);
        FillDatabase.fillCategories(repository);
    }

    public static synchronized RatingDbService getInstance(Application application){
        if(instance == null){
            instance = new RatingDbService(application);
        }
        return instance;
    }

    public void deleteAllUserPreferences(Application application){
        repository.deleteAll();
        FillDatabase.fillCategories(repository);
    }

    public void updateUserPreference(UserPreferenceRoomModel preference){
        Log.d("RIGHTEXIT", "updateUserPreference");
        repository.update(preference);
    }

    public LiveData<List<UserPreferenceRoomModel>> getAllUserPreferences(){
        return repository.getAllUserPreferences();
    }

}
