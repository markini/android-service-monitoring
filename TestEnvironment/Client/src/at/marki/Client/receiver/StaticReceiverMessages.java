package at.marki.Client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import at.marki.Client.MainActivity;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.Message;
import at.marki.Client.utils.NotificationCreator;
import at.marki.Client.utils.Parser;

/**
 * Created by marki on 29.10.13.
 */
public class StaticReceiverMessages extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.newMessage = true;
        Message messageObject = Parser.parseMessage(intent);
        Data.addMessage(context, messageObject);
        Data.getMessages(context).add(messageObject);
        NotificationCreator.makeNotification(context);
        abortBroadcast();
    }


}