package io.github.yoshi1123.adbio;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.appwidget.AppWidgetManager;

public final class ADBio extends android.app.Activity {

    private ADBInterface adbi;
    private String error_msg;

    public ADBio() {
        super();
        try {
            adbi = new ADBInterface();
        } catch (SUException e) {
            // textV.setText("Error: Could not get super user priviledge.");
            error_msg = e.getMessage();
        }
    }

    private void displayAdb(TextView tv) {
        int adbEnabled = adbi.getAdb();
        if (adbEnabled != -1)
            tv.setText("ADB Debugging: "+(adbEnabled!=0?"Enabled":"Disabled")+"");
        else
            tv.setText("Error: Could not access settings");
    }

    private void updateWidget() {
        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Intent intent = new Intent(context, ADBioAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, ADBioAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    protected @Override void onResume() {
        super.onResume();

        if (adbi == null) return;

        final TextView textV = (TextView) findViewById(R.id.text);
        displayAdb(textV);
    }

    protected @Override void onCreate(final android.os.Bundle activityState) {
        super.onCreate(activityState);

        setContentView(R.layout.activity_main);

        final TextView textV = (TextView) findViewById(R.id.text);
        final Button buttonEnable = (Button) findViewById(R.id.btnEnable);
        final Button buttonDisable = (Button) findViewById(R.id.btnDisable);

        if (adbi == null) {
            textV.setText(error_msg);
            buttonEnable.setVisibility(View.GONE);
            buttonDisable.setVisibility(View.GONE);
            return;
        }

        // get and display initial adb debugging state
        displayAdb(textV);

        buttonEnable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adbi.setAdb(1);
                displayAdb(textV);
                updateWidget();
            }
        });

        buttonDisable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adbi.setAdb(0);
                displayAdb(textV);
                updateWidget();
            }
        });

    }

}
