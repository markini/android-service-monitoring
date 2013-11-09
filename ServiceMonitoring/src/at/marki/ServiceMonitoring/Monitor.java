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

    private ScheduledFuture handler;

    /**
     * @return true if no problem and false if monitoring found a problem
     */
    protected abstract boolean monitorThis(Context context);

    protected abstract boolean handleProblem();

    public void executeMonitoring(Context context, boolean startSticky, int intervalInMinutes) {
        long interval = intervalInMinutes * 60 * 1000;
        if (startSticky) {
            startAlarmTask(context, interval);
        } else {
            startExecutionService(context, interval);
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

    private void startExecutionService(final Context context, long interval) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runner = new Runnable() {
            public void run() {
                System.out.println("Monitor this from executorservice");
                if(!monitorThis(context)){
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
    public void onReceive(final Context context, Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Monitor this from intent service - from alarmmanager");
                if(!monitorThis(context)){
                    handleProblem();
                }
            }
        }).start();
    }

    public boolean isRunning(){
        return handler != null;
    }
}
