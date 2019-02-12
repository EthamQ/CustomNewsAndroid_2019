package com.example.rapha.swipeprototype2.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.jobScheduler.NewsOfTheDayScheduler;

public class NewsOfTheDayNotificationService {

    public static void sendNotificationLoadedDailyNews(NewsOfTheDayScheduler scheduler){
        int NOTIFICATION_ID = 233;
        NotificationManager notificationManager = (NotificationManager) scheduler.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";

            // Channel
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription("Description");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        // Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(scheduler.getApplication(), "my_channel_01")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Daily News")
                .setContentText("Your daily news just have been updated!")
                .setTicker("Ticker")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
