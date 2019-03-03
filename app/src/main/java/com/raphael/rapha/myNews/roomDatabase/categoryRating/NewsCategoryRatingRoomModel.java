package com.raphael.rapha.myNews.roomDatabase.categoryRating;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity()
public class NewsCategoryRatingRoomModel {
    @NonNull
    @PrimaryKey
    private int newsCategoryId;

    private int rating;

    public NewsCategoryRatingRoomModel(int newsCategoryId, int rating) {
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
