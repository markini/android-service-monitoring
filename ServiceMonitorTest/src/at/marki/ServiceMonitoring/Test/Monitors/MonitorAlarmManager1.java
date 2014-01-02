package at.marki.ServiceMonitoring.Test.Monitors;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import at.marki.ServiceMonitoring.Monitor;
import at.marki.ServiceMonitoring.Test.StickyMonitorTests.TestStickyMonitors;

/**
 * Created by marki on 02.01.14.
 */
public class MonitorAlarmManager1 extends Monitor {

    @Override
    protected boolean observeThis(Context context) {
        System.out.println("OBSERVE THIS - MonitorAlarmManager1");
        TestStickyMonitors.observeThisCounter++;
        return true;
    }

    @Override
    protected boolean handleEvent(Context context) {
        System.out.println("HANDLE EVENT - MonitorAlarmManager1");
        TestStickyMonitors.handleEventCounter++;
        return true;
    }

    public static final Parcelable.Creator<Monitor> CREATOR
            = new Parcelable.Creator<Monitor>() {
        public Monitor createFromParcel(Parcel in) {
            return new MonitorAlarmManager1();
        }

        public Monitor[] newArray(int size) {
            return new MonitorAlarmManager1[size];
        }
    };
}

