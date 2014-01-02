package at.marki.ServiceMonitoring.Test.Monitors;

import android.content.Context;
import android.os.Parcel;
import at.marki.ServiceMonitoring.Monitor;
import at.marki.ServiceMonitoring.Test.StickyMonitorTests.TestStickyMonitors;

/**
 * Created by marki on 02.01.14.
 */
public class MonitorAlarmManager2 extends Monitor {

    @Override
    protected boolean observeThis(Context context) {
        System.out.println("OBSERVE THIS - MonitorAlarmManager2");
        TestStickyMonitors.observeThisCounter++;
        return false;
    }

    @Override
    protected boolean handleEvent(Context context) {
        System.out.println("HANDLE EVENT - MonitorAlarmManager2");
        TestStickyMonitors.handleEventCounter++;
        return true;
    }

    public static final Creator<Monitor> CREATOR
            = new Creator<Monitor>() {
        public Monitor createFromParcel(Parcel in) {
            return new MonitorAlarmManager2();
        }

        public Monitor[] newArray(int size) {
            return new MonitorAlarmManager2[size];
        }
    };
}

