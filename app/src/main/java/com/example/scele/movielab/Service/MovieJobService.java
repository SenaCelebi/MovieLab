package com.example.scele.movielab.Service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

public class MovieJobService extends JobService {
        @Override
        public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);

        return true;
        }

        private void doBackgroundWork(final JobParameters params) {

        Context context = MovieJobService.this;
        ReminderTasks.executeTask(context,
                ReminderTasks.ACTION_NO_CHECK);

        jobFinished(params, false);
        }

        @Override
        public boolean onStopJob(JobParameters params) {
        return true;
        }
}
