package io.github.yoshi1123.adbio;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.appwidget.AppWidgetManager;


public final class Utility {

    private Utility() { }

    /**
     * Broadcasts an action to call the home screen widget's
     * <code>AppWidgetProvider.onUpdate</code> method.
     *
     * @param context  the application containing the widget whose update method
     *                 is to be called
     */
    public static void updateWidget(final Context context) {
        AppWidgetManager appWidgetManager
            = AppWidgetManager.getInstance(context);
        Intent intent = new Intent(context, ADBioAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, ADBioAppWidgetProvider.class)
        );
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

}
