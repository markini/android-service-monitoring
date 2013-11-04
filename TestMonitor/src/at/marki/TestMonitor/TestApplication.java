package at.marki.TestMonitor;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import at.marki.TestMonitor.receiver.AlarmReceiver;
import at.marki.TestMonitor.services.ScheduledExecutor;
import timber.log.Timber;

import java.util.Calendar;
import java.util.TimeZone;

public class TestApplication extends Application {

    public static final int minutes = 1;
    public static ScheduledExecutor executor;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        //setup broadcast
        Timber.d("starting set recurring alarm!");
        //setRecurringAlarm(getApplicationContext());
        if (executor == null) {
            executor = new ScheduledExecutor(); //runs even when program is "closed" with back - but doesn't survive closed program with task manager // when app dies - executorservice stopps
        }
        //executor.execute();
    }

    public void setRecurringAlarm(Context context) { //confirmed for staying active when display is off, when closed app, even when closed app in taskmanager
        Timber.d("Start alarm");
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        updateTime.set(Calendar.HOUR_OF_DAY, 11);
        updateTime.set(Calendar.MINUTE, 45);
        Intent downloader = new Intent(context, AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(getBaseContext(), 0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 4000, minutes * 60 * 1000, recurringDownload); // starts in 4 seconds//screams every minute

        //set executor
        executor.execute();
    }

    public void cancelAlarm(Context context) {
        Timber.d("Cancel alarm");
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        executor.handler.cancel(true);
    }

//    public void setRecurringAlarm2(Context context){ // dies like executorservice when app closes - but then I could also use executorservice
//        AlarmManager alarm = (AlarmManager)context.getSystemService(ALARM_SERVICE);
//        final String SOME_ACTION = "at.marki.dynamic.receiver";
//        IntentFilter intentFilter = new IntentFilter(SOME_ACTION);
//        DynamicReceiver mReceiver = new DynamicReceiver();
//        context.registerReceiver(mReceiver, intentFilter);
//        Intent i2 = new Intent(SOME_ACTION);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i2, 0);
//        alarm.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 4000, minutes * 60 * 1000, pi); // starts in 4 seconds
//    }
//
//    class DynamicReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent arg1) {
//
//            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.GERMANY);
//            Date resultTime = new Date(System.currentTimeMillis());
//
//            Timber.d("Dynamic Alarm Test " + sdf.format(resultTime) + " ------------------------------------------------------------------->>>>>>>>>>>>>>>>>>");
//        }
//    }


}
