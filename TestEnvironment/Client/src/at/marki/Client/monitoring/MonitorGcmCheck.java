package at.marki.Client.monitoring;

import android.content.Context;
import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

/**
 * Created by marki on 06.11.13.
 */
public class MonitorGcmCheck extends Monitor {

    @Override
    public boolean monitorThis(Context context) {
        return PingGcm.performPing(context);
    }

    @Override
    public boolean handleProblem() {
        Timber.e("in handleProblem");
        return false;
    }
}
