package at.marki.TestMonitor;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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

    public static final Parcelable.Creator<Monitor> CREATOR
            = new Parcelable.Creator<Monitor>() {
        public Monitor createFromParcel(Parcel in) {
            return new TestMonitor2();
        }

        public Monitor[] newArray(int size) {
            return new TestMonitor2[size];
        }
    };
}
