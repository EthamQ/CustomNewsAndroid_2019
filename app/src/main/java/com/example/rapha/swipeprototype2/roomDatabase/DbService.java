package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class DbService {

    private static DbService instance;
    UserPreferenceRepository repository;

    private DbService(Application application){
        this.repository = new UserPreferenceRepository(application);
        FillDatabase.fillDatabase(repository);
    }

    public static synchronized DbService getInstance(Application application){
        if(instance == null){
            instance = new DbService(application);
        }
        return instance;
    }

    public void deleteAllUserPreferences(Application application){
        repository.deleteAll();
    }

    public void updateUserPreference(UserPreferenceRoomModel preference){
        repository.update(preference);
    }

    public LiveData<List<UserPreferenceRoomModel>> getAllUserPreferences(){
        return repository.getAllUserPreferences();
    }

}
