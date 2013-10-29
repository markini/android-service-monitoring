package at.marki.Client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by marki on 29.10.13.
 */
public class Settingshandler {

    public static String getDownloadAddress(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return "http://" + prefs.getString("tag_server_ip", null) + ":1433" + "/getData";
    }
}
