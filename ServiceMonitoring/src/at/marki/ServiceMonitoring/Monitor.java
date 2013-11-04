package at.marki.ServiceMonitoring;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by marki on 04.11.13.
 */
public abstract class Monitor extends BroadcastReceiver {

    private Context context;
    private boolean startSticky;
    private long interval;

    public ScheduledFuture handler;

    public abstract boolean monitorThis();
    public abstract boolean handleProblem();

    public Monitor(Context context, boolean startSticky, int intervalInMinutes ){
        this.context = context;
        this.startSticky = startSticky;
        this.interval = intervalInMinutes * 60 * 1000;
    }

    public void executeMonitoring(){
        if(startSticky){
            startAlarmTask();
        }else{
            startExecutionService();
        }
    }

    public void stopMonitoring(){
        if(startSticky){
            Intent intent = new Intent(context, this.getClass());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }else{
            if(handler != null){
                handler.cancel(true);
            }
        }
    }

    private void startExecutionService(){

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runner = new Runnable() {
            public void run() {
                //TODO start monitor this
            }
        };

        handler = scheduler.scheduleAtFixedRate(runner, 1000, interval, MILLISECONDS); //starts in one second
    }

    private void startAlarmTask(){
        Intent intent = new Intent(context, this.getClass());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //why service: see this: http://shuklaxyz.blogspot.co.at/2012/03/is-starting-thread-in-broadcast.html
        //TODO start monitor this --> this runs in ui thread! start new thread
    }

    public class IntentServiceForReceiver extends IntentService{

        public IntentServiceForReceiver(String name) {
            super(name);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            monitorThis();
        }
    }

    public class ServiceForReceiver extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            monitorThis();
            return START_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
