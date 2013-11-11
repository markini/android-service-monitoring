package at.marki.TestMonitor;

import android.content.Context;
import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

/**
 * Created by marki on 04.11.13.
 */
public class TestMonitor2 extends Monitor {

    @Override
    public boolean monitorThis(Context context) {
        Timber.d("Monitor this called - sticky - alarmservice");
        return true;
    }

    @Override
    public boolean handleProblem(Context context) {
        Timber.d("handle problem called - sticky - alarmservice");
        return true;
    }
}
