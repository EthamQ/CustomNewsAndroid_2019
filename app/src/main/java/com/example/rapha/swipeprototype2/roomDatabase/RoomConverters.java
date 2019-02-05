package com.example.rapha.swipeprototype2.roomDatabase;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class RoomConverters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
