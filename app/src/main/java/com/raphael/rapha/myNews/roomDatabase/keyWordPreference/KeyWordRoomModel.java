package com.raphael.rapha.myNews.roomDatabase.keyWordPreference;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "KeyWordPreference")
public class KeyWordRoomModel {

    @Ignore
    public static final int UNSET = 0;
    @Ignore
    public static final int LIKED = 1;
    @Ignore
    public static final int DISLIKED = 2;

    @NonNull
    @PrimaryKey()
    public String keyWord;

    public int categoryId;
    public int status;
    public Date shownToUser;
    public boolean usedInArticleOfTheDay;

    public KeyWordRoomModel(String keyWord, int categoryId){
        this.keyWord = keyWord;
        this.categoryId = categoryId;
        status = UNSET;
        usedInArticleOfTheDay = false;
    }

    @Override
    public String toString(){
        String string = "Keyword: " + keyWord + "\n";
        string += "categoryId: " + categoryId + "\n";
        string += "status: " + status + "\n";
        if(shownToUser != null){
            string += "shownToUser: " + shownToUser.toString() + "\n";
        }
        else{
            string += "shownToUser: " + shownToUser + "\n";
        }

        return string;
    }
}
