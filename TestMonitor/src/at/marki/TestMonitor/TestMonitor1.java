package at.marki.TestMonitor;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import at.marki.ServiceMonitoring.Monitor;
import timber.log.Timber;

/**
 * Created by marki on 04.11.13.
 */
public class TestMonitor1 extends Monitor {

    @Override
    public boolean observeThis(Context context) {
        Timber.d("Monitor this called execution process");
        return true;
    }

    @Override
    public boolean handleEvent(Context context) {
        Timber.d("Handle problem called execution process");
        return true;
    }

    public static final Parcelable.Creator<Monitor> CREATOR
            = new Parcelable.Creator<Monitor>() {
        public Monitor createFromParcel(Parcel in) {
            return new TestMonitor1();
        }

        public Monitor[] newArray(int size) {
            return new TestMonitor1[size];
        }
    };
}
