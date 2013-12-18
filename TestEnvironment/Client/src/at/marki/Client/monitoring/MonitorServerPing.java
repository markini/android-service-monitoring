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
    public boolean observeThis(Context context) {
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
    public boolean handleEvent(Context context) {
        Timber.e("in handleEvent");
        Settingshandler.setServerState(context,false);
	    //TODO send sms and mail to admin (this is me, and this is a test environment - so better not)
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
