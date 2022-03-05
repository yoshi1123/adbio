package io.github.yoshi1123.adbio;

import android.util.Log;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;


public class ADBioAppWidgetProvider extends AppWidgetProvider {

    private ADBInterface adbi;

    public ADBioAppWidgetProvider() {
        super();
        adbi = new ADBInterface();
        Log.d("ADBio", "CONSTRUCTOR");
    }

    /**
     * Setup home screen widget button initialized to state of USB ADB.
     * <p>
     * Set to a green <code>Button</code> if USB ADB is enabled or set to a red
     * <code>Button</code> if USB ADB is disabled, then add on click listeners
     * to send a broadcast indicating whether to enable or disable USB ADB.
     */
    @Override
    public void onUpdate(
            final Context context,
            final AppWidgetManager appWidgetManager,
            final int[] appWidgetIds
    ) {

        // schedule a ServiceJob that updates the widget when the setting
        // changes from outside of ADBio
        SettingsJob.scheduleJob(context);

        try {
            Log.d("ADBio", "UPDATE");
            final int n = appWidgetIds.length;

            for (int i = 0; i < n; ++i) {

                Intent intentE = new Intent(context, getClass());
                intentE.setAction("adb_enable");
                intentE.putExtra("WIDGET_ID", appWidgetIds[i]);
                PendingIntent piE = PendingIntent.getBroadcast(context, 0,
                        intentE, PendingIntent.FLAG_UPDATE_CURRENT);
                Intent intentD = new Intent(context, getClass());
                intentD.setAction("adb_disable");
                intentD.putExtra("WIDGET_ID", appWidgetIds[i]);
                PendingIntent piD = PendingIntent.getBroadcast(context, 0,
                        intentD, PendingIntent.FLAG_UPDATE_CURRENT);
                Log.d("ADBio", "  sent widget id (getExtra): "
                        + intentE.getIntExtra("WIDGET_ID", -1));

                // get layout for the App Widget and attach onClick listener to
                // widget button
                RemoteViews views = new RemoteViews(context.getPackageName(),
                        R.layout.widget_layout);
                if (adbi.getAdb() == 1) {
                    views.setViewVisibility(R.id.btnAdbEnabled, View.VISIBLE);
                    views.setViewVisibility(R.id.btnAdbDisabled, View.GONE);
                } else if (adbi.getAdb() == 0) {
                    views.setViewVisibility(R.id.btnAdbEnabled, View.GONE);
                    views.setViewVisibility(R.id.btnAdbDisabled, View.VISIBLE);
                }
                views.setOnClickPendingIntent(R.id.btnAdbEnabled,
                        piD);
                views.setOnClickPendingIntent(R.id.btnAdbDisabled,
                        piE);

                appWidgetManager.updateAppWidget(appWidgetIds[i], views);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Handle 'adb_enable' and 'adb_disable' broadcasts to update the remote
     * views for the home screen ADB button.
     *
     * @param context  the application to which the remote views are associated
     * @param intent   the broadcast action
     */
    public void onReceive(final Context context, final Intent intent) {
        Log.d("ADBio", "RECEIVE ACTION: " + intent.getAction());
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        if (intent.getAction() == "adb_enable") {
            Log.d("ADBio", "ONRECEIVE adb_enable");
            Log.d("ADBio", "  ADB==1");
            adbi.setAdb(1);
            views.setViewVisibility(R.id.btnAdbEnabled, View.VISIBLE);
            views.setViewVisibility(R.id.btnAdbDisabled, View.GONE);
            int appWidgetId = intent.getIntExtra("WIDGET_ID", -1);
            Log.d("ADBio", "  received back extra: " + appWidgetId);
            // if (appWidgetId == -1)
            //     throw new IllegalArgumentException("Receiver cannot find"
            //     + "extra WIDGET_ID");
            AppWidgetManager appWidgetManager
                = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } else if (intent.getAction() == "adb_disable") {
            Log.d("ADBio", "ONRECEIVE adb_disable");
            Log.d("ADBio", "  ADB==1");
            adbi.setAdb(0);
            views.setViewVisibility(R.id.btnAdbEnabled, View.GONE);
            views.setViewVisibility(R.id.btnAdbDisabled, View.VISIBLE);
            int appWidgetId = intent.getIntExtra("WIDGET_ID", -1);
            Log.d("ADBio", "  received back extra: " + appWidgetId);
            // if (appWidgetId == -1)
            //     throw new IllegalArgumentException("Receiver cannot find"
            //     + "extra WIDGET_ID");
            AppWidgetManager appWidgetManager
                = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }


        super.onReceive(context, intent);
    }

}
