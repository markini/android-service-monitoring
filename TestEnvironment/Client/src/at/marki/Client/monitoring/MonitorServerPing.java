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
public class MonitorServerPing extends Monitor {

    @Override
    public boolean monitorThis(Context context) {
//        if(!CheckConnectivityState.performConnectivityCheck(context)){
//            Settingshandler.setConnectivityState(context,false);
//            return false;
//        }

        if(PingServer.performPing(context)){
            Settingshandler.setServerState(context,true);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean handleProblem(Context context) {
        Timber.e("in handleProblem");
        Settingshandler.setServerState(context,false);
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
