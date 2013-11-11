package at.marki.Client.monitoring;

import android.content.Context;
import at.marki.Client.utils.Settingshandler;
import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

/**
 * Created by marki on 06.11.13.
 */
public class MonitorConnectivity extends Monitor {

    @Override
    public boolean monitorThis(Context context) {
        if(CheckConnectivityState.performConnectivityCheck(context)){
            Settingshandler.setConnectivityState(context, true);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean handleProblem(Context context) {
        Settingshandler.setConnectivityState(context, false);
        Timber.e("in handleProblem");
        return false;
    }
}
