package io.github.yoshi1123.adbio;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


class SUException extends RuntimeException {
    public SUException() { super(); }
    public SUException(String message) { super(message); }
    public SUException(Throwable cause) { super(cause); }
    public SUException(String message, Throwable cause) { super(message, cause); }
}


public class ADBInterface {

    private final Process su;
    private final DataOutputStream sout;
    private final BufferedReader sin;

    public ADBInterface() {
        try {
            su = Runtime.getRuntime().exec("su");
        } catch (Exception e) {
            throw new SUException("Error: Could not get super user priviledge");
        }
        sout = new DataOutputStream(su.getOutputStream());
        sin = new BufferedReader(new InputStreamReader(su.getInputStream()));
    }

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

    public void setAdb(int v) {
        try {
            sout.writeBytes("settings put global adb_enabled "+v+"\n");
            sout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
