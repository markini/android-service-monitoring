package at.marki.Client.monitoring;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import at.marki.Client.EventHandler;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.Log;
import at.marki.Client.utils.Settingshandler;
import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

import java.util.UUID;

/**
 * Created by marki on 06.11.13.
 */
public class MonitorConnectivity extends Monitor {

    @Override
    public boolean observeThis(Context context) {
        Log logEntry = new Log(UUID.randomUUID().toString(),"",System.currentTimeMillis());

        if(CheckConnectivityState.performConnectivityCheck(context)){
            Settingshandler.setConnectivityState(context, true);
            logEntry.logMessage = "Connectivity check succeeded.";
            Data.addLogEntry(context,logEntry);
            return true;
        }else{
            logEntry.logMessage = "Connectivity check failed.";
            Data.addLogEntry(context,logEntry);
            return false;
        }
    }

    @Override
    public boolean handleEvent(Context context) {
        Settingshandler.setConnectivityState(context, false);
        Timber.e("in handleEvent");
	    EventHandler.calculateApplicationEvent(context);
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
