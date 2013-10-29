package at.marki.Client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gcm.GCMBaseIntentService;
import com.squareup.otto.Bus;
import timber.log.Timber;

import javax.inject.Inject;

public class GCMIntentService extends GCMBaseIntentService {

    public static final String SENDER_ID = "380505122106";

    @Inject
    Bus bus;

    @Override
    protected String[] getSenderIds(Context context) {
        String[] ids = new String[1];
        ids[0] = SENDER_ID;
        return ids;
    }

    @Override
    protected void onError(Context context, String arg1) {
        Timber.e("ERROR " + arg1);
    }

    @Override
    protected void onMessage(Context context, Intent messageIntent) {
        Timber.i("Received new message");

        Bundle bundle = messageIntent.getExtras();

        String message = bundle.getString("message");
        if (message == null) {
            message = "no message";
        }

        Timber.d("new message from server: " + message);

        Intent broadCastIntent = new Intent(this.getString(R.string.intent_filter_message_receive));
        broadCastIntent.putExtra("message",message);
        this.sendOrderedBroadcast(broadCastIntent, "at.marki.Client.BROADCASTNOTIFY");

    }

    @Override
    protected void onRegistered(Context context, String id) {
        Timber.i("onRegister called - starting sendGcmIdTask");

    }

    @Override
    protected void onUnregistered(Context context, String arg1) {
        Timber.i("onUnRegistered called");
    }

}