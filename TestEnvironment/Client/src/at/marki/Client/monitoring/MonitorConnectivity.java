package at.marki.Client.monitoring;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import at.marki.Client.utils.Settingshandler;
import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

/**
 * Created by marki on 06.11.13.
 */
public class MonitorConnectivity extends Monitor {

    @Override
    public boolean observeThis(Context context) {
        if(CheckConnectivityState.performConnectivityCheck(context)){
            Settingshandler.setConnectivityState(context, true);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean handleEvent(Context context) {
        Settingshandler.setConnectivityState(context, false);
        Timber.e("in handleEvent");
        return false;
    }

    public static final Parcelable.Creator<Monitor> CREATOR
            = new Parcelable.Creator<Monitor>() {
        public Monitor createFromParcel(Parcel in) {
            return new MonitorConnectivity();
        }

        public Monitor[] newArray(int size) {
            return new MonitorConnectivity[size];
        }
    };
}
