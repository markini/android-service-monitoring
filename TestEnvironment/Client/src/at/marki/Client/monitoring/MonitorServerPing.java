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
public class MonitorServerPing extends Monitor {

    @Override
    public boolean observeThis(Context context) {
//        if(!CheckConnectivityState.performConnectivityCheck(context)){
//            Settingshandler.setConnectivityState(context,false);
//            return false;
//        }

        Log logEntry = new Log(UUID.randomUUID().toString(),"",System.currentTimeMillis());

        if(PingServer.performPing(context)){
            Settingshandler.setServerState(context,true);
            logEntry.logMessage = "Server check succeeded.";
            Data.addLogEntry(context, logEntry);
            return true;
        }else{
            logEntry.logMessage = "Server check failed.";
            Data.addLogEntry(context, logEntry);
            return false;
        }
    }

    @Override
    public boolean handleEvent(Context context) {
        Timber.e("in handleEvent");
        Settingshandler.setServerState(context,false);
	    //TODO send sms and mail to admin (this is me, and this is a test environment - so better not)
	    EventHandler.calculateApplicationEvent(context);
        return false;
    }

    public static final Parcelable.Creator<Monitor> CREATOR
            = new Parcelable.Creator<Monitor>() {
        public Monitor createFromParcel(Parcel in) {
            return new MonitorServerPing();
        }

        public Monitor[] newArray(int size) {
            return new MonitorServerPing[size];
        }
    };
}
