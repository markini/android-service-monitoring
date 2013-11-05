package at.marki.TestMonitor;

import android.app.Application;
import timber.log.Timber;

public class TestApplication extends Application {

    public static TestMonitor1 monitor1;
    public static TestMonitor2 monitor2;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        if(monitor1 == null){
            monitor1 = new TestMonitor1();
        }
        if(monitor2 == null){
            monitor2 = new TestMonitor2();
        }
    }

    public void setRecurringAlarm(){
        monitor1.executeMonitoring(this,false,1);
        monitor2.executeMonitoring(this,true,2);
    }

    public void cancelAlarm(){
        monitor1.stopMonitoring(this);
        monitor2.stopMonitoring(this);
    }
}
