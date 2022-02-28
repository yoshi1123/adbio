package io.github.yoshi1123.adbio;

import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.app.job.JobScheduler;
import android.app.job.JobInfo;
import android.app.job.JobService;
import android.app.job.JobParameters;
import android.provider.Settings;
import android.net.Uri;

public class SettingsJob extends JobService {

    static final JobInfo JOB_INFO;

    static {
        ComponentName serviceComponent = new ComponentName("io.github.yoshi1123.adbio", SettingsJob.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        Uri uri = Settings.Global.getUriFor(Settings.Global.ADB_ENABLED);
        builder.addTriggerContentUri(new JobInfo.TriggerContentUri(uri, 0));
        JOB_INFO = builder.build();
    }

    public static void scheduleJob(Context context) {
        JobScheduler js = context.getSystemService(JobScheduler.class);
        js.schedule(JOB_INFO);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("ADBio", "JOB STARTED");
        Utility.updateWidget(getApplicationContext());
        scheduleJob(getApplicationContext());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
