package com.example.rapha.swipeprototype2.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        return dateToISO8601(currentDate);
    }

    public static String dateToISO8601(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
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

    public static int daysBetween(Date date1, Date date2){
        DateTime dateJoda1 = new DateTime(date1);
        DateTime dateJoda2 = new DateTime(date2);
        return Days.daysBetween(dateJoda1, dateJoda2).getDays();
    }

    public static int hoursBetween(Date date1, Date date2){
        DateTime dateJoda1 = new DateTime(date1);
        DateTime dateJoda2 = new DateTime(date2);
        return Hours.hoursBetween(dateJoda1, dateJoda2).getHours();
    }


    public static long dateToLong(Date date){
        return date.getTime();
    }

    public static Date longToDate(long dateMills){
        return new Date(dateMills);
    }

    public static String makeDateReadable(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy | hh:mm a", Locale.ENGLISH);
        return dateFormat.format(date);
    }
}
