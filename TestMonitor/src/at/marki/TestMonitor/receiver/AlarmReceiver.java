package at.marki.TestMonitor.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import at.marki.TestMonitor.R;
import timber.log.Timber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marki on 01.11.13.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.GERMANY);
        Date resultTime = new Date(System.currentTimeMillis());

        Timber.d("Alarm went off! yeah " + sdf.format(resultTime) + "  ------------------------------------------------------------->>>>>>>>>>>>> ");
        // notify
        makeNotification(context);
    }

    private void makeNotification(Context context) {

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Testapp notification header")
                .setContentText("test alarm text").setLargeIcon(largeIcon);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.build();

        // Hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(R.id.notification_id, notification);
    }
}