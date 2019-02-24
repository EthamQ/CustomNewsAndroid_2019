package com.raphael.rapha.myNews.roomDatabase.keyWordPreference;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface IKeyWordDao {

    @Query("SELECT * FROM KeyWordPreference")
    LiveData<List<KeyWordRoomModel>> getAllKeyWords();

    @Query("SELECT * FROM KeyWordPreference WHERE usedInArticleOfTheDay = :usedInArticleOfTheDay")
    LiveData<List<KeyWordRoomModel>> getAllKeyWords(boolean usedInArticleOfTheDay);

    @Query("SELECT * FROM KeyWordPreference WHERE status = :status")
    LiveData<List<KeyWordRoomModel>> getKeyWordsByStatus(int status);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOneKeyWord(KeyWordRoomModel keyWordRoomModel);

    @Query("DELETE FROM KeyWordPreference")
    void deleteAllKeyWords();

    @Update
    void updateKeyWord(KeyWordRoomModel keyWordRoomModel);
}
