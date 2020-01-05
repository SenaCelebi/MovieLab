package com.example.scele.movielab.Service;

import android.content.Context;

import com.example.scele.movielab.Utilities.NotificationUtils;

public class ReminderTasks {

    public static final String ACTION_NO_CHECK = "no-check";
    public static final String ACTION_DISMISS_NOTIFICATION  = "dismiss-notification";
    public static final String ACTION_YES_CHECK = "yes-check";

    public static void executeTask(Context context, String action){
        if (ACTION_NO_CHECK.equals(action)){
            issueChargingReminder(context);
        }
        else if (ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }
        else if (ACTION_YES_CHECK.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }
    }

    private static void issueChargingReminder(Context context) {
        NotificationUtils.remindUserBecauseCharging(context);
    }
}
