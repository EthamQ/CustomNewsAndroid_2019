package com.raphael.rapha.myNews.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.raphael.rapha.myNews.R;

public class NotificationService {

    public static NotificationCompat.Builder getDefaultNotificationBuilder(int notificationId, Application application, String title, String content){
        // Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(application, "my_channel_01")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_newspaper_colored)
                .setAutoCancel(true);
        return builder;
        }

        public static void sendNotification(Application application, NotificationCompat.Builder builder, int notificationId){
            NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String CHANNEL_ID = "my_channel_01";
                CharSequence name = "my_channel";
                // Channel
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription("Description");
                mChannel.enableLights(true);
                mChannel.enableVibration(true);
                mChannel.setShowBadge(false);
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(notificationId, builder.build());
        }
}
