package com.example.scele.movielab.Utilities;

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
import android.support.v4.content.ContextCompat;

import com.example.scele.movielab.DiscussionActivity;
import com.example.scele.movielab.HomeActivity;
import com.example.scele.movielab.R;
import com.example.scele.movielab.SearchActivity;

public class NotificationUtils {

    private static final int MOVIE_REMINDER_NOTIFICATION_ID  = 1138;
    private static final int MOVIE_REMINDER_PENDING_INTENT_ID       = 3417;
    private static final String MOVIE_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";

    private static final int    ACTION_IGNORE_PENDING_INTENT_ID        = 14;
    private static final int ACTION_DRINK_PENDING_INTENT_ID = 1;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserBecauseCharging(Context context){
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    MOVIE_REMINDER_NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mChannel);
        }

        Intent yesIntent = new Intent(context, DiscussionActivity.class);
        yesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent yesPendingIntent = PendingIntent.getActivity(context,ACTION_DRINK_PENDING_INTENT_ID, yesIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent noIntent = new Intent(context, HomeActivity.class);
        noIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent noPendingIntent = PendingIntent.getActivity(context,ACTION_IGNORE_PENDING_INTENT_ID, noIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(context, MOVIE_REMINDER_NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setColor(ContextCompat.getColor(context,R.color.colorBackground))
                .setSmallIcon(R.drawable.logo2)
                .setLargeIcon(largeIcon(context))
                .setContentTitle("CineLab")
                .setContentText(context.getString(R.string.text_notification))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.text_notification)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
        .addAction(R.drawable.ic_play_arrow_black_24dp,context.getString(R.string.yes_notification), yesPendingIntent )
        .addAction(R.drawable.ic_play_arrow_black_24dp, context.getString(R.string.no_notification), noPendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(MOVIE_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static Bitmap largeIcon(Context context) {
        Resources res       = context.getResources();
        Bitmap    largeIcon = BitmapFactory.decodeResource(res, R.drawable.logo2);
        return largeIcon;
    }


    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, HomeActivity.class);
        return PendingIntent.getActivity(
                context,
                MOVIE_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
