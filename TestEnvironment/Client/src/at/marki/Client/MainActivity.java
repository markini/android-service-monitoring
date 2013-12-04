package at.marki.Client;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import at.marki.Client.events.newMessageEvent;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.Message;
import com.google.android.gcm.GCMRegistrar;
import com.squareup.otto.Bus;
import timber.log.Timber;

import javax.inject.Inject;

public class MainActivity extends Activity {

    private BroadcastReceiver messageReceiver;

    public static boolean newMessage = false;

    @Inject
    Bus bus;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((ClientApplication) getApplication()).inject(this);
        manageGCM();
        Fragment fragmentMain = getFragmentManager().findFragmentByTag(FragmentMain.TAG);
        if (fragmentMain == null) {
            fragmentMain = new FragmentMain();
        }

        startTransaction(R.id.fragment_frame, fragmentMain, FragmentMain.TAG, false);
    }

    void manageGCM() {
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("") || !GCMRegistrar.isRegistered(this)) {
            GCMRegistrar.register(this, GCMIntentService.SENDER_ID);
            Timber.d("received ID: " + GCMRegistrar.getRegistrationId(this));
        } else {
            if (BuildConfig.DEBUG) {
                Timber.d("Already registered");
            }
        }

        GCMRegistrar.setRegisteredOnServer(this, true);
    }

    public void startTransaction(int id, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(id, fragment, tag);
        if (addToBackStack) {
            fragTransaction.addToBackStack(null);
        }
        fragTransaction.commit();
    }

    //--------------------------------------------------------------------------------------------------
    //PAUSE - RESUME -----------------------------------------------------------------------------------

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("on resume called");

        messageReceiver = new MessageReceiver();

        IntentFilter filterMessage = new IntentFilter(getString(R.string.intent_filter_message_receive));
        filterMessage.setPriority(30);

        this.registerReceiver(messageReceiver, filterMessage);
    }

    @Override
    protected void onPause() {
        Timber.d("onPause called");
        this.unregisterReceiver(messageReceiver);
        super.onPause();
    }

    //--------------------------------------------------------------------------------------------------
    //RECEIVER -----------------------------------------------------------------------------------------

    private class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = null;
            String messageId = null;
            Handler MAIN_THREAD = new Handler(Looper.getMainLooper());
            if (intent != null) {
                try {
                    message = intent.getExtras().getString("message");
                    messageId = intent.getExtras().getString("messageId");
                } catch (Exception e) {
                    Timber.e("no extras in on receive of broadcast");
                }
            } else {
                message = null;
                messageId = null;
            }
            if (message == null) {
                message = "defaultMessage";
            }
            if (messageId == null) {
                messageId = "defaultId";
            }

            final Message sendMessage = new Message(messageId, message, System.currentTimeMillis());
            Data.addMessage(context, sendMessage);
            MAIN_THREAD.post(new Runnable() {
                @Override
                public void run() {
                    bus.post(new newMessageEvent(sendMessage));
                }
            });
            abortBroadcast();
        }
    }
}
