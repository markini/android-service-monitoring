package at.marki.Client.monitoring;

import android.content.Context;
import at.marki.Client.utils.Settingshandler;
import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

/**
 * Created by marki on 06.11.13.
 */
public class MonitorGcmCheck extends Monitor {

    @Override
    public boolean monitorThis(Context context) {
        if(!CheckConnectivityState.performConnectivityCheck(context)){
            Settingshandler.setConnectivityState(context,false);
            return false;
        }else{
            Settingshandler.setConnectivityState(context,true);
        }

        if(!PingServer.performPing(context)){
            Settingshandler.setServerState(context,false);
            return false;
        }else{
            Settingshandler.setServerState(context,true);
        }

        if(PingGcm.performPing(context)){
            Settingshandler.setGcmState(context, true);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean handleProblem(Context context) {
        Settingshandler.setGcmState(context, false);
        Timber.e("in handleProblem");
        return false;
    }
}
