package com.example.rapha.swipeprototype2.roomDatabase.languageCombination;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rapha.swipeprototype2.roomDatabase.requestOffset.RequestOffsetRoomModel;

import java.util.List;

@Dao
public interface ILanguageCombinationDao {

    @Query("SELECT * FROM language_combination")
    LiveData<List<LanguageCombinationRoomModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOne(LanguageCombinationRoomModel languageCombinationRoomModel);

    @Update
    void update(LanguageCombinationRoomModel languageCombinationRoomModel);
}
