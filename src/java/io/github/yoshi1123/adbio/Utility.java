package io.github.yoshi1123.adbio;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.appwidget.AppWidgetManager;

public class Utility {

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Intent intent = new Intent(context, ADBioAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, ADBioAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

}
