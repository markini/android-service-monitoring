package at.marki.Client.monitoring;

import android.content.Context;
import at.marki.ServiceMonitoring.Monitor;

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
        return false;
    }
}
