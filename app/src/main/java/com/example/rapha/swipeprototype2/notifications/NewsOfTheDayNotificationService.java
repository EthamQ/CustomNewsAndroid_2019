package com.example.rapha.swipeprototype2.notifications;

import com.example.rapha.swipeprototype2.jobScheduler.NewsOfTheDayJobScheduler;

public class NewsOfTheDayNotificationService {

    private static boolean allowDebugMessages = false;

    public static void sendNotificationLoadedDailyNews(NewsOfTheDayJobScheduler scheduler){
        int NOTIFICATION_ID = 233;
        String title = "Daily News";
        String content = "Your daily newsfeed has just been updated!";
        NotificationService.sendNotificaton(NOTIFICATION_ID, scheduler.getApplication(), title, content);
    }

    public static void sendNotificationDebug(NewsOfTheDayJobScheduler scheduler, String debugMessage, int id){
        if(allowDebugMessages){
            int NOTIFICATION_ID = 1;
            String title = "debugMessage";
            String content = "";
            NotificationService.sendNotificaton(id, scheduler.getApplication(), debugMessage, content);
        }
    }

}
