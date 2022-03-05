package io.github.yoshi1123.adbio;

import android.util.Log;
import android.content.Context;
import android.content.ComponentName;
import android.app.job.JobScheduler;
import android.app.job.JobInfo;
import android.app.job.JobService;
import android.app.job.JobParameters;
import android.provider.Settings;
import android.net.Uri;


public class SettingsJob extends JobService {

    /**
     * The <code>JobInfo</code> containing this <code>JobService</code> to be
     * scheduled when the USB ADB setting is changed.
     */
    static final JobInfo JOB_INFO;

    static {
        ComponentName serviceComponent
            = new ComponentName("io.github.yoshi1123.adbio",
                    SettingsJob.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        Uri uri = Settings.Global.getUriFor(Settings.Global.ADB_ENABLED);
        builder.addTriggerContentUri(new JobInfo.TriggerContentUri(uri, 0));
        JOB_INFO = builder.build();
    }

    /**
     * Schedule the job to listen with a <code>ContentObserver</code> until the
     * USB ADB setting changes.
     *
     * @param context  the application to run the job
     */
    public static void scheduleJob(final Context context) {
        JobScheduler js = context.getSystemService(JobScheduler.class);
        js.schedule(JOB_INFO);
    }

    /**
     * Updates the home screen widget to reflect USB ADB setting.
     * <p>
     * This is method is triggered when the USB ADB setting changes. It
     * completes the job (and I believe cleans up the observer), so another job
     * has to be scheduled again to observe the next change to the USB ADB
     * setting.
     *
     * @return <code>false</code> to indicate the job has completed
     *         (synchronously)
     */
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.d("ADBio", "JOB STARTED");
        Utility.updateWidget(getApplicationContext());
        scheduleJob(getApplicationContext());
        return false;
    }

    /**
     * Should not be called, as the system will never cancel our job as it is
     * run synchronously.
     *
     * @return <code>false</code> to end the job entirely without rescheduling
     * it
     */
    @Override
    public boolean onStopJob(final JobParameters params) {
        return false;
    }

}
