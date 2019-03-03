package com.raphael.rapha.myNews.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRepository;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRoomModel;

import java.util.List;

public class RatingDbService {

    private static RatingDbService instance;
    NewsCategoryRatingRepository repository;

    private RatingDbService(Application application){
        this.repository = new NewsCategoryRatingRepository(application);
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

    public void updateUserPreference(NewsCategoryRatingRoomModel preference){
        Log.d("RIGHTEXIT", "updateUserPreference");
        repository.update(preference);
    }

    public LiveData<List<NewsCategoryRatingRoomModel>> getAllUserPreferences(){
        return repository.getAllUserPreferences();
    }

}
