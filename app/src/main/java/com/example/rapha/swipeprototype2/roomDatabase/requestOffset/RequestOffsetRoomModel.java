package com.example.rapha.swipeprototype2.roomDatabase.requestOffset;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;

import java.util.Date;

@Entity(tableName = "request_offset", primaryKeys = {"categoryId", "languageCombination"},
        foreignKeys = @ForeignKey(
                entity = LanguageCombinationRoomModel.class,
        parentColumns = "id",
        childColumns = "languageCombination"
        ),
        indices = {@Index(value = {"categoryId", "languageCombination"}, unique = true),
        }
)
public class RequestOffsetRoomModel {

    public int categoryId;
    public String requestOffset;
    public long languageCombination;

    public RequestOffsetRoomModel(){ }

    @Override
    public String toString(){
        String ret = "";
        ret += "categoryId: " + categoryId;
        ret += ", offset: " + requestOffset;
        ret += ", comboId: " + languageCombination;
        return ret;
    }
}
