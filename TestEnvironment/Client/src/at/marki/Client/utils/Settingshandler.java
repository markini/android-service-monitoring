package at.marki.Client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by marki on 29.10.13.
 */
public class Settingshandler {

    // download address ------------------------------------------------------------------

    public static String getDownloadAddress(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return "http://" + prefs.getString("tag_server_ip", null) + ":1433" + "/getData";
    }

    public static String getPingServerAddress(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return "http://" + prefs.getString("tag_server_ip", null) + ":1433" + "/ping";
    }

    public static String getGcmCheckAddress(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return "http://" + prefs.getString("tag_server_ip", null) + ":1433" + "/gcmCheck";
    }

    // server ---------------------------------------------------------------------------

    public static boolean getServerState(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("tag_server_state",true);
    }

    public static void setServerState(Context context, boolean state){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("tag_server_state",state).commit();
    }

    // gcm -----------------------------------------------------------------------------

    public static boolean getGcmState(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("tag_gcm_state",true);
    }

    public static void setGcmState(Context context, boolean state){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("tag_gcm_state",state).commit();
    }

    // connectivity ---------------------------------------------------------------------

    public static boolean getConnectivityState(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("tag_connectivity_state",true);
    }

    public static void setConnectivityState(Context context, boolean state){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("tag_connectivity_state",state).commit();
    }
}
