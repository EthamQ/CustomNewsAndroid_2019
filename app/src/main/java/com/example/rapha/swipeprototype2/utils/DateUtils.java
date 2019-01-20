package com.example.rapha.swipeprototype2.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * Given year, month and date it returns a String containing the date
     * in ISO8601 format yyyy-MM-dd.
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String dateToISO8601(String year, String month, String day){
        //TODO: handle missing leading zeros
        return year + "-" + month + "-" + day;
    }

    /**
     * Subtracts "daysBefore" from the current date and returns the
     * calculated date in the format yyyy-MM-dd
     * @param daysBefore
     * @return
     */
    public static String getDateBefore(int daysBefore){
        Date currentDate = new Date();
        addDays(currentDate, daysBefore * -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentDate);
    }

    /**
     * Add the value of "days" to "date".
     * Directly changes the Date object.
     * @param date
     * @param days
     */
    private static void addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        date.setTime( c.getTime().getTime() );
    }
}
