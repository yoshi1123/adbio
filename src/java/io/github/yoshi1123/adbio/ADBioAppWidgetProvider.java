package io.github.yoshi1123.adbio;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.os.Looper;
import android.os.Handler;
import android.provider.Settings;

import android.util.Log;

public class ADBioAppWidgetProvider extends AppWidgetProvider {

    // private final String COLOR_ENABLED = "#33999900";  // holo light
    // private final String COLOR_DISABLED = "#ffff4444";
    private final String COLOR_ENABLED = "#ff669900";  // holo dark
    private final String COLOR_DISABLED = "#ffcc0000";

    private ADBInterface adbi;

    public ADBioAppWidgetProvider() {
        super();
        adbi = new ADBInterface();
        Log.d("ADBio", "CONSTRUCTOR");
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {

        ADBContentObserver co = new ADBContentObserver(context, new Handler(Looper.getMainLooper()));
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(Settings.Global.ADB_ENABLED), false, co);

        try {
            Log.d("ADBio", "UPDATE");
            final int N = appWidgetIds.length;

            for (int i = 0; i < N; ++i) {

                Intent intent_e = new Intent(context, getClass());
                intent_e.setAction("adb_enable");
                intent_e.putExtra("WIDGET_ID", appWidgetIds[i]);
                PendingIntent pi_e = PendingIntent.getBroadcast(context, 0,
                        intent_e, PendingIntent.FLAG_UPDATE_CURRENT);
                Intent intent_d = new Intent(context, getClass());
                intent_d.setAction("adb_disable");
                intent_d.putExtra("WIDGET_ID", appWidgetIds[i]);
                PendingIntent pi_d = PendingIntent.getBroadcast(context, 0,
                        intent_d, PendingIntent.FLAG_UPDATE_CURRENT);
                Log.d("ADBio", "  sent widget id (getExtra): "+ intent_e.getIntExtra("WIDGET_ID", -1));

                // get layout for the App Widget and attach onClick listener to
                // widget button
                RemoteViews views = new RemoteViews(context.getPackageName(),
                        R.layout.widget_layout);
                if (adbi.getAdb() == 1) {
                    views.setViewVisibility(R.id.btnAdbEnabled, View.VISIBLE);
                    views.setViewVisibility(R.id.btnAdbDisabled, View.GONE);
                }
                else if (adbi.getAdb() == 0)
                {
                    views.setViewVisibility(R.id.btnAdbEnabled, View.GONE);
                    views.setViewVisibility(R.id.btnAdbDisabled, View.VISIBLE);
                }
                views.setOnClickPendingIntent(R.id.btnAdbEnabled,
                        pi_d);
                views.setOnClickPendingIntent(R.id.btnAdbDisabled,
                        pi_e);

                appWidgetManager.updateAppWidget(appWidgetIds[i], views);

            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void onReceive(Context context, Intent intent) {
        Log.d("ADBio", "RECEIVE ACTION: "+intent.getAction());
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        if (intent.getAction() == "adb_enable") {
            Log.d("ADBio", "ONRECEIVE adb_enable");
            Log.d("ADBio", "  ADB==1");
            adbi.setAdb(1);
            views.setViewVisibility(R.id.btnAdbEnabled, View.VISIBLE);
            views.setViewVisibility(R.id.btnAdbDisabled, View.GONE);
            int appWidgetId = intent.getIntExtra("WIDGET_ID", -1);
            Log.d("ADBio", "  received back extra: "+appWidgetId);
            // if (appWidgetId == -1)
            //     throw new IllegalArgumentException("Receiver cannot find extra WIDGET_ID");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } else if (intent.getAction() == "adb_disable") {
            Log.d("ADBio", "ONRECEIVE adb_disable");
            Log.d("ADBio", "  ADB==1");
            adbi.setAdb(0);
            views.setViewVisibility(R.id.btnAdbEnabled, View.GONE);
            views.setViewVisibility(R.id.btnAdbDisabled, View.VISIBLE);
            int appWidgetId = intent.getIntExtra("WIDGET_ID", -1);
            Log.d("ADBio", "  received back extra: "+appWidgetId);
            // if (appWidgetId == -1)
            //     throw new IllegalArgumentException("Receiver cannot find extra WIDGET_ID");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }


        super.onReceive(context, intent);
    }

}
