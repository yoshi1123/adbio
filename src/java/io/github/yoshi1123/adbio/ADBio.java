package io.github.yoshi1123.adbio;

import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Context;


/**
 * ADBio is an Activity that enables/disables USB ADB.
 */
public final class ADBio extends android.app.Activity {

    private ADBInterface adbi;

    /**
     * The error message in the case root is not available.
     */
    private String errorMsg;

    public ADBio() {
        super();
        try {
            adbi = new ADBInterface();
        } catch (SUException e) {
            // textV.setText("Error: Could not get super user priviledge.");
            errorMsg = e.getMessage();
        }
    }

    /**
     * Set the <code>TextView</code> to indicate if ADB is enabled.
     *
     * @param tv  the <code>TextView</code> to set
     */
    private void displayAdb(final TextView tv) {
        int adbEnabled = adbi.getAdb();
        if (adbEnabled != -1)
            tv.setText("ADB Debugging: " + (
                        adbEnabled != 0 ? "Enabled" : "Disabled") + "");
        else
            tv.setText("Error: Could not access settings");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adbi == null) return;

        final TextView textV = (TextView) findViewById(R.id.text);
        displayAdb(textV);
    }

    @Override
    protected void onCreate(final android.os.Bundle activityState) {
        super.onCreate(activityState);

        setContentView(R.layout.activity_main);

        final Context context = getApplicationContext();
        final TextView textV = (TextView) findViewById(R.id.text);
        final Button buttonEnable = (Button) findViewById(R.id.btnEnable);
        final Button buttonDisable = (Button) findViewById(R.id.btnDisable);

        if (adbi == null) {
            textV.setText(errorMsg);
            buttonEnable.setVisibility(View.GONE);
            buttonDisable.setVisibility(View.GONE);
            return;
        }

        // get and display initial adb debugging state
        displayAdb(textV);

        buttonEnable.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                adbi.setAdb(1);
                displayAdb(textV);
                Utility.updateWidget(context);
            }
        });

        buttonDisable.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                adbi.setAdb(0);
                displayAdb(textV);
                Utility.updateWidget(context);
            }
        });
    }

}
