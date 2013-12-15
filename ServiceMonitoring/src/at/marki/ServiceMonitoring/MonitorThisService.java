package at.marki.ServiceMonitoring;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by marki on 19.11.13.
 */
public class MonitorThisService extends IntentService {

    public MonitorThisService() {
        super("MonitorThisService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("Monitor this from intent service - from alarmmanager");
        Monitor monitor = intent.getExtras().getParcelable("monitor");
        if(!monitor.observeThis(this)){
            monitor.handleEvent(this);
        }
    }
}