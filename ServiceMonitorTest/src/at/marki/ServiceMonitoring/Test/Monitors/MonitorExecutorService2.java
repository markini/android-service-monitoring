package at.marki.ServiceMonitoring.Test.Monitors;

import android.content.Context;
import android.os.Parcel;
import at.marki.ServiceMonitoring.Monitor;
import at.marki.ServiceMonitoring.Test.NonStickyMonitorTests.TestNonStickyMonitors;

/**
 * Created by marki on 02.01.14.
 */
public class MonitorExecutorService2 extends Monitor {

    @Override
    protected boolean observeThis(Context context) {
        System.out.println("OBSERVE THIS - MonitorExecutorService2");
        TestNonStickyMonitors.observeThisCounter++;
        return false;
    }

    @Override
    protected boolean handleEvent(Context context) {
        System.out.println("HANDLE EVENT - MonitorExecutorService2");
        TestNonStickyMonitors.handleEventCounter++;
        return true;
    }

    public static final Creator<Monitor> CREATOR
            = new Creator<Monitor>() {
        public Monitor createFromParcel(Parcel in) {
            return new MonitorExecutorService2();
        }

        public Monitor[] newArray(int size) {
            return new MonitorExecutorService2[size];
        }
    };
}

