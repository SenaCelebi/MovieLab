package com.example.scele.movielab.Service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class MovieReminderIntentService extends IntentService {

    public MovieReminderIntentService() {
        super("MovieReminderIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}
