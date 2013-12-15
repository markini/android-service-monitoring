package at.marki.Client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import at.marki.Client.MainActivity;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.Message;
import at.marki.Client.utils.NotificationCreator;
import timber.log.Timber;

import java.util.UUID;

/**
 * Created by marki on 14.12.13.
 */
public class StaticSmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Message messageObject = new Message(UUID.randomUUID().toString(),intent.getExtras().getString("message"),System.currentTimeMillis());
		Timber.i("Static sms Receiver");
		if(messageObject != null){
			MainActivity.newMessage = true;
			Data.addMessage(context, messageObject);
			Data.getMessages(context).add(messageObject);
			NotificationCreator.makeNotification(context);
			abortBroadcast();
		}
	}
}