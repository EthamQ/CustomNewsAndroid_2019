package com.raphael.rapha.myNews.roomDatabase.requestOffset;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface IRequestOffsetDao {

    @Query("SELECT * FROM request_offset")
    LiveData<List<RequestOffsetRoomModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(RequestOffsetRoomModel requestOffsetRoomModel);

    @Update
    void update(RequestOffsetRoomModel requestOffsetRoomModel);
}
