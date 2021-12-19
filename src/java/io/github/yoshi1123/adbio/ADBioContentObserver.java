package io.github.yoshi1123.adbio;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;


final class ADBContentObserver extends android.database.ContentObserver {

    private Context context;

    public ADBContentObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
    }

    public @Override void onChange(boolean selfChange) {
        try {
            int adbEnabled = Settings.Global.getInt(this.context.getContentResolver(), Settings.Global.ADB_ENABLED);
            Utility.updateWidget(this.context);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

}
