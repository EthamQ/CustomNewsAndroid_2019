package com.example.rapha.swipeprototype2.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.jobScheduler.NewsOfTheDayJobScheduler;
import com.example.rapha.swipeprototype2.sharedPreferencesAccess.SettingsService;

public class NewsOfTheDayNotificationService {

    public static void sendNotificationLoadedDailyNews(NewsOfTheDayJobScheduler scheduler){
        int NOTIFICATION_ID = 233;
        String title = "News of the day";
        String content = "Your daily newsfeed has just been updated!";

        NotificationCompat.Builder builder = NotificationService.getDefaultNotificationBuilder(NOTIFICATION_ID, scheduler.getApplication(), title, content);
        Intent notificationIntent = new Intent(scheduler.getApplication(), MainActivity.class);
        notificationIntent.putExtra("menuFragment", "newsOfTheDayFragment");
        PendingIntent contentIntent = PendingIntent.getActivity(scheduler.getApplication(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        if(SettingsService.getCheckedNotification(scheduler)){
            NotificationService.sendNotification(scheduler.getApplication(), builder, NOTIFICATION_ID);
        }
    }


}
