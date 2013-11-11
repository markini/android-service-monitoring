package at.marki.Client.monitoring;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import timber.log.Timber;

/**
 * Created by marki on 06.11.13.
 */
class CheckConnectivityState {
    public static boolean performConnectivityCheck(Context context) {
        try {
            Timber.d("MONITOR CONNECTIVITY!!!");

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
