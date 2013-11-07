package at.marki.Client.monitoring;

import android.content.Context;
import at.marki.ServiceMonitoring.Monitor;

/**
 * Created by marki on 06.11.13.
 */
public class MonitorServerPing extends Monitor {

    @Override
    public boolean monitorThis(Context context) {
        return PingServer.performPing(context);
    }

    @Override
    public boolean handleProblem() {
        return false;
    }
}
