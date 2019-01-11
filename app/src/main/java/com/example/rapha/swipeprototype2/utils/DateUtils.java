package com.example.rapha.swipeprototype2.utils;

public class DateUtils {

    /**
     * Given year, month and date it returns a String containing the date
     * in ISO8601 format e.g. 2019-01-08.
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String dateToISO8601(String year, String month, String day){
        //TODO: handle missing leading zeros
        return year + "-" + month + "-" + day;
    }
}
