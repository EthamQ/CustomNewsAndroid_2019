package com.example.rapha.swipeprototype2.roomDatabase.languageCombination;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "language_combination")
public class LanguageCombinationRoomModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public boolean german;
    public boolean russian;
    public boolean french;
    public boolean english;
    public boolean japanese;
}
