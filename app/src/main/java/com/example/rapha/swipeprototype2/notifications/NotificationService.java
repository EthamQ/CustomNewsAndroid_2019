package com.example.rapha.swipeprototype2.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.rapha.swipeprototype2.R;

public class NotificationService {

    public static void sendNotificaton(int notificationId, Application application, String title, String content){
        NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);

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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(application, "my_channel_01")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setTicker("Ticker")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        notificationManager.notify(notificationId, builder.build());
    }
}
