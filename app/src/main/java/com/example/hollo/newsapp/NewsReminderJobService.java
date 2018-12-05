package com.example.hollo.newsapp;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NewsReminderJobService extends JobService {
    static AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {


            @Override
            protected Object doInBackground(Object[] params) {
                Context context  =NewsReminderJobService.this;
                ReminderTasks.executeTask(NewsReminderJobService.this, ReminderTasks.ACTION_REMINDER);
                //NewsItemRepository.syncNews();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }
        };
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(false);
        return true;
    }
}
