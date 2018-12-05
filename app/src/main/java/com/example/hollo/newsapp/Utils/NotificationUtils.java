package com.example.hollo.newsapp.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;

import com.example.hollo.newsapp.MainActivity;
import com.example.hollo.newsapp.NewsReminderIntentService;
import com.example.hollo.newsapp.R;
import com.example.hollo.newsapp.ReminderTasks;


public class NotificationUtils {
    private static final int NEWS_REFRESH_REMINDER_NOTIFICATION_ID = 1138;
    private static final int NEWS_REFRESH_REMINDER_PENDING_INTENT_ID = 3417;
    private static final String NEWS_REFRESH_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private static final int ACTION_REFRESH_PENDING_INTENT_ID = 1;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUser(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NEWS_REFRESH_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.primary),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NEWS_REFRESH_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.android_refresh)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.refresh_title))
                .setContentText(context.getString(R.string.refresh_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.refresh_now)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(refreshNewsAction(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NEWS_REFRESH_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, NewsReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Action ignoreReminderAction = new Action(R.drawable.ic_cancel_black_24px,
                "No...",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static Action refreshNewsAction(Context context) {
        Intent refreshNewsIntent = new Intent(context, NewsReminderIntentService.class);
        refreshNewsIntent.setAction(ReminderTasks.ACTION_REFRESH_NEWS);
        PendingIntent refreshNewsPendingIntent = PendingIntent.getService(
                context,
                ACTION_REFRESH_PENDING_INTENT_ID,
                refreshNewsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Action refreshNewsAction = new Action(R.drawable.android_refresh,
                "Updating",
                refreshNewsPendingIntent);
        return refreshNewsAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                NEWS_REFRESH_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.android_refresh);
        return largeIcon;
    }
}