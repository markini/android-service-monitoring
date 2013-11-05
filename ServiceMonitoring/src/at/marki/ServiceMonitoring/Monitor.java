package at.marki.ServiceMonitoring;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by marki on 04.11.13.
 */
public abstract class Monitor extends BroadcastReceiver {

    public ScheduledFuture handler;

    /**
     * @return returns true if no problem and fals if monitoring found a problem
     */
    public abstract boolean monitorThis();

    public abstract boolean handleProblem();

    public void executeMonitoring(Context context, boolean startSticky, int intervalInMinutes) {
        long interval = intervalInMinutes * 60 * 1000;
        if (startSticky) {
            startAlarmTask(context, interval);
        } else {
            startExecutionService(interval);
        }
    }

    public void stopMonitoring(Context context) {
        System.out.println("Abort monitoring");

        //kill alarm manager
        Intent intent = new Intent(context, this.getClass());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        //kill execution process
        if (handler != null) {
            handler.cancel(true);
        }
    }

    private void startExecutionService(long interval) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runner = new Runnable() {
            public void run() {
                System.out.println("Monitor this from executorservice");
                if(!monitorThis()){
                    handleProblem();
                }
            }
        };

        handler = scheduler.scheduleAtFixedRate(runner, 1000, interval, MILLISECONDS); //starts in one second
    }

    private void startAlarmTask(Context context, long interval) {
        Intent intent = new Intent(context, this.getClass());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 1000, interval, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Monitor this from intent service - from alarmmanager");
                if(!monitorThis()){
                    handleProblem();
                }
            }
        }).start();
        //why service: see this: http://shuklaxyz.blogspot.co.at/2012/03/is-starting-thread-in-broadcast.html
    }
}
