package com.example.rapha.swipeprototype2.utils;

import android.text.format.DateUtils;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateService {

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

    public static String dateToISO8601(String year, String month, String day, String hour, String minute, String second){
        //TODO: handle missing leading zeros
        return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second + "Z";
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

    public static String subtractSecond(String dateISO8601, int seconds){
        int expectedLengthISOFormat = 20;
        int startIndexSeconds = 17;
        if(dateISO8601.length() == expectedLengthISOFormat){
            String secondValueString = dateISO8601.substring(startIndexSeconds, startIndexSeconds + 2);
            int secondValueInt = Integer.valueOf(secondValueString);
            if(secondValueInt - seconds >= 0){
                secondValueInt -= seconds;
            }
            else{
                return subtractMinute(dateISO8601, 1);
            }
            DecimalFormat decimalFormat = new DecimalFormat("00");
            secondValueString = decimalFormat.format(secondValueInt);
            String partBeforeSeconds = dateISO8601.substring(0, startIndexSeconds);
            String partAfterSeconds = dateISO8601.substring(startIndexSeconds + 2, expectedLengthISOFormat);
            String newDate = partBeforeSeconds + secondValueString + partAfterSeconds;
            return newDate;
        } else return dateISO8601;
    }

    public static String subtractMinute(String dateISO8601, int minutes){
        int expectedLengthISOFormat = 20;
        int startIndexMinutes = 11;
        if(dateISO8601.length() == expectedLengthISOFormat){
            String minuteValueString = dateISO8601.substring(startIndexMinutes, startIndexMinutes + 2);
            int minuteValueInt = Integer.valueOf(minuteValueString);
            if(minuteValueInt - minutes >= 0){
                minuteValueInt -= minutes;
            }
            else{
                return subtractHour(dateISO8601, 1);
            }
            DecimalFormat decimalFormat = new DecimalFormat("00");
            minuteValueString = decimalFormat.format(minuteValueInt);
            String partBeforeMinutes = dateISO8601.substring(0, startIndexMinutes);
            String partAfterMinutes = dateISO8601.substring(startIndexMinutes + 2, expectedLengthISOFormat);
            String newDate = partBeforeMinutes + minuteValueString + partAfterMinutes;
            return newDate;
        } else return dateISO8601;
    }

    public static String subtractHour(String dateISO8601, int hours){
        int expectedLengthISOFormat = 20;
        int startIndexHour = 14;
        if(dateISO8601.length() == expectedLengthISOFormat){
            String hourValueString = dateISO8601.substring(startIndexHour, startIndexHour + 2);
            int hourValueInt = Integer.valueOf(hourValueString);
            if(hourValueInt - hours >= 0){
                hourValueInt -= hours;
            }
            DecimalFormat decimalFormat = new DecimalFormat("00");
            hourValueString = decimalFormat.format(hourValueInt);
            String partBeforeHour = dateISO8601.substring(0, startIndexHour);
            String partAfterHour = dateISO8601.substring(startIndexHour + 2, expectedLengthISOFormat);
            String newDate = partBeforeHour + hourValueString + partAfterHour;
            return newDate;
        } else return dateISO8601;
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
