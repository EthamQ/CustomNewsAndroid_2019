package com.example.rapha.swipeprototype2.roomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface IUserPreferenceDao {
    @Query("SELECT * FROM UserPreference WHERE newsCategoryId = :newsCategoryId")
    UserPreferenceTable getOne(int newsCategoryId);
    @Insert
    void insertOneNewsCategory (UserPreferenceTable userPreferenceTable);
    @Query("SELECT * FROM UserPreference")
    LiveData<List<UserPreferenceTable>> getAllUserPreferences();
}
