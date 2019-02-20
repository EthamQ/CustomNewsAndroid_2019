package com.example.rapha.swipeprototype2.requestDateOffset;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.roomDatabase.requestOffset.RequestOffsetRoomModel;

import java.util.LinkedList;
import java.util.List;

public class DateOffsetService {

    public static List<RequestOffsetRoomModel> getOffsetsForLanguageCombinationId(List<RequestOffsetRoomModel> offsets, int languageCombinationId) {
        List<RequestOffsetRoomModel> relevantOffsets = new LinkedList<>();
        for (int i = 0; i < offsets.size(); i++) {
            RequestOffsetRoomModel o = offsets.get(i);
            if(o.languageCombination == languageCombinationId){
                relevantOffsets.add(o);
                Log.d("newswipe", "******* REAL FOR NEXT REQUEST: cat:" + o.categoryId + ", offs: " + o.requestOffset);
            }
        }
        return relevantOffsets;
    }
}
