package at.marki.Client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gcm.GCMBaseIntentService;
import timber.log.Timber;

public class GCMIntentService extends GCMBaseIntentService {

    public static final String SENDER_ID = "380505122106";

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

        String id = bundle.getString("id");
        if (id == null) {
            Timber.e("no id found in message - abort");
            return;
        }
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