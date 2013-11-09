package at.marki.Client.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import at.marki.Client.ClientApplication;
import com.squareup.otto.Bus;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * Created by marki on 29.10.13.
 */
public class GetNewDataService extends Service {

    @Inject
    private
    Bus bus;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ((ClientApplication) getApplication()).inject(this);
        Timber.d("starting get data service");

        try {
            executeTask();
        } catch (Exception e) {
            stopSelf();
            e.printStackTrace();
        }
        return START_STICKY;
    }

    private void executeTask() {
        GetNewDataAsyncTask task = new GetNewDataAsyncTask(bus, this);
        task.execute();
    }

}
