package at.marki.Client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gcm.GCMBaseIntentService;
import de.akquinet.android.androlog.Log;

public class GCMIntentService extends GCMBaseIntentService {

    public static final String SENDER_ID = "471176147026";

    @Override
    protected String[] getSenderIds(Context context) {
        String[] ids = new String[1];
        ids[0] = SENDER_ID;
        return ids;
    }

    @Override
    protected void onError(Context context, String arg1) {
        Log.e(this, "ERROR " + arg1);
    }

    @Override
    protected void onMessage(Context context, Intent messageIntent) {
        Log.i(this, "Received new message");

        Bundle bundle = messageIntent.getExtras();

        String id = bundle.getString("id");
        if (id == null) {
            Log.e(this, "no id found in message - abort");
            return;
        }

        // start download service
//		Intent intent = new Intent(context, ProjectLoaderService.class);
//		intent.putExtra("id", id);
//		context.startService(intent);
    }

    @Override
    protected void onRegistered(Context context, String id) {
        Log.i(TAG, "onRegister called - starting sendGcmIdTask");

//        Intent intent = new Intent(this, RegisterGcmIdService.class);
//        context.startService(intent);
    }

    @Override
    protected void onUnregistered(Context context, String arg1) {
        Log.i(TAG, "onUnRegistered called");
//        if (GCMRegistrar.isRegisteredOnServer(context)) {
//            Intent intent = new Intent(context, UnregisterGcmService.class);
//            context.startService(intent);
//        }
    }

}