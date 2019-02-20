package com.example.rapha.swipeprototype2.roomDatabase.languageCombination;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "language_combination", indices = {@Index(value = {"german", "russian", "french", "english", "japanese"},
        unique = true)})
public class LanguageCombinationRoomModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public boolean german = false;
    public boolean russian = false;
    public boolean french = false;
    public boolean english = false;
    public boolean japanese = false;

    @Override
    public String toString(){
        String ret = "";
        ret += "Id: " + id;
        ret += ", german: " + german;
        ret += ", russian: " + russian;
        ret += ", french: " + french;
        ret += ", english: " + english;
        return ret;
    }
}
