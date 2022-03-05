package io.github.yoshi1123.adbio;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


class SUException extends RuntimeException {
    SUException() { super(); }
    SUException(final String message) { super(message); }
    SUException(final Throwable cause) { super(cause); }
    SUException(final String message, final Throwable cause) {
        super(message, cause);
    }
}


public class ADBInterface {

    private final Process su;
    private final DataOutputStream sout;
    private final BufferedReader sin;

    /**
     * Class constructor to initialize a 'su' process.
     */
    public ADBInterface() {
        try {
            su = Runtime.getRuntime().exec("su");
        } catch (Exception e) {
            throw new SUException("Error: Could not get super user priviledge");
        }
        sout = new DataOutputStream(su.getOutputStream());
        sin = new BufferedReader(new InputStreamReader(su.getInputStream()));
    }

    /**
     * Returns whether USB ADB is enabled.
     * @return 1 if enabled, 0 if disabled, or -1 on error
     */
    public int getAdb() {
        try {
            sout.writeBytes("settings get global adb_enabled\n");
            sout.flush();
            String result = sin.readLine();
            return Integer.parseInt(result);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Enables or disables USB ADB.
     *
     * @param v  1 to enable; 0 to disable
     */
    public void setAdb(final int v) {
        try {
            sout.writeBytes("settings put global adb_enabled " + v + "\n");
            sout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
