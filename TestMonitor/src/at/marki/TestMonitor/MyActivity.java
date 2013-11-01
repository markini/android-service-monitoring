package at.marki.TestMonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import timber.log.Timber;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void clickCancelButton(View view){
        ((TestApplication)getApplication()).cancelAlarm(this);
    }

    public void clickStartButton(View view){
        Timber.d("restart alarm");
        ((TestApplication)getApplication()).setRecurringAlarm(this);
    }

    //TODO: implement ScheduledExecutorService
}
