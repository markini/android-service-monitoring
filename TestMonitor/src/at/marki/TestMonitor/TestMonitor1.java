package at.marki.TestMonitor;

import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

/**
 * Created by marki on 04.11.13.
 */
public class TestMonitor1 extends Monitor {

    @Override
    public boolean monitorThis() {
        Timber.d("Monitor this called execution process");
        return true;
    }

    @Override
    public boolean handleProblem() {
        Timber.d("Handle problem called execution process");
        return true;
    }
}
