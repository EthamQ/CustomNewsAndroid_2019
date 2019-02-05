package com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "KeyWordPreference")
public class KeyWordRoomModel {

    @NonNull
    @PrimaryKey()
    public String keyWord;

    public int categoryId;
    public boolean liked;

    public KeyWordRoomModel(String keyWord, int categoryId){
        this.keyWord = keyWord;
        this.categoryId = categoryId;
        liked = false;
    }
}
