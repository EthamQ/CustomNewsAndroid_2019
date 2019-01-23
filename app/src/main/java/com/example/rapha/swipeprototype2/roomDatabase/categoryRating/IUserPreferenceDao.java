package com.example.rapha.swipeprototype2.roomDatabase.categoryRating;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface IUserPreferenceDao {
    @Query("SELECT * FROM UserPreference WHERE newsCategoryId = :newsCategoryId")
    UserPreferenceRoomModel getOne(int newsCategoryId);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOneNewsCategory(UserPreferenceRoomModel userPreferenceRoomModel);
    @Query("SELECT * FROM UserPreference")
    LiveData<List<UserPreferenceRoomModel>> getAllUserPreferences();
    @Query("DELETE FROM UserPreference")
    void deleteAllPreferences();
    @Update
    void updateUserPreference(UserPreferenceRoomModel userPreferenceRoomModel);
}
