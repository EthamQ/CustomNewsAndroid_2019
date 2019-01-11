package com.example.rapha.swipeprototype2.roomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "UserPreference")
public class UserPreferenceRoomModel {
    @NonNull
    @PrimaryKey
    private int newsCategoryId;

    private int rating;

    public UserPreferenceRoomModel(int newsCategoryId, int rating) {
        this.newsCategoryId = newsCategoryId;
        this.rating = rating;
    }

    public int getNewsCategoryId(){ return this.newsCategoryId; }
    public int getRating(){ return this.rating; }

    @Override
    public String toString(){
        return "Category: " + newsCategoryId + ", Rating: " + rating;
    }



}
