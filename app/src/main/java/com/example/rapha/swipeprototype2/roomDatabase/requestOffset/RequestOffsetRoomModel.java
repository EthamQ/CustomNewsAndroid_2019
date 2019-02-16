package com.example.rapha.swipeprototype2.roomDatabase.requestOffset;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;

@Entity(tableName = "request_offset", primaryKeys = {"categoryId", "languageCombination"},
        foreignKeys = @ForeignKey(
                entity = LanguageCombinationRoomModel.class,
        parentColumns = "id",
        childColumns = "languageCombination"
        )
)
public class RequestOffsetRoomModel {

    public int categoryId;
    public int requestOffset;
    public int languageCombination;

    public RequestOffsetRoomModel(){ }
}
