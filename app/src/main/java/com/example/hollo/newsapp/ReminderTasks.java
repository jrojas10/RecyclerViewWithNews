package com.example.hollo.newsapp;

import android.content.Context;

import com.example.hollo.newsapp.Utils.NotificationUtils;
import com.example.hollo.newsapp.models.NewsItemRepository;


public class ReminderTasks {

    public static final String ACTION_REFRESH_NEWS = "refresh-news";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String ACTION_REMINDER = "reminder";

    public static void executeTask(Context context, String action) {
        if (ACTION_REFRESH_NEWS.equals(action)) {
            refreshNews(context);
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_REMINDER.equals(action)) {
            issueReminder(context);
        }
    }

    private static void refreshNews(Context context) {
        NewsItemRepository.syncNews();
        NotificationUtils.clearAllNotifications(context);
    }

    private static void issueReminder(Context context) {
        NotificationUtils.remindUser(context);
    }
}