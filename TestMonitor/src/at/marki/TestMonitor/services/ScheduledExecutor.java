package at.marki.TestMonitor.services;

import timber.log.Timber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScheduledExecutor {

    //ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor(); //TODO ???????
    public ScheduledFuture handler;

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void execute() {
        final Runnable beeper = new Runnable() {
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.GERMANY);
                Date resultTime = new Date(System.currentTimeMillis());
                Timber.d("Executor service " + sdf.format(resultTime) +" ------------------------------------------------->>>>>>>>>>>>>>>>>>>");
            }
        };

        handler = scheduler.scheduleAtFixedRate(beeper, 10, 60, SECONDS);
        //final ScheduledFuture beeperHandle = handler;

//        scheduler.schedule(new Runnable() {
//            public void run() {
//                beeperHandle.cancel(true);
//            }
//        }, 60 * 60, SECONDS);
    }
}
