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
import at.marki.Client.receiver.GCMIntentService;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.Message;
import at.marki.Client.utils.Parser;
import com.google.android.gcm.GCMRegistrar;
import com.squareup.otto.Bus;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.UUID;

public class MainActivity extends Activity {

	private BroadcastReceiver messageReceiver;
	private BroadcastReceiver smsReceiver;

	public static boolean newMessage = false;

	@Inject
	Bus bus;

	protected static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

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
		smsReceiver = new SmsDynamicReceiver();

		//filter for normal messages (gcm receiver)
		IntentFilter filterMessage = new IntentFilter(getString(R.string.intent_filter_message_receive));
		filterMessage.setPriority(30);

		//filter for sms messages
		IntentFilter filterSmsMessage = new IntentFilter(getString(R.string.intent_filter_sms_receive));
		filterSmsMessage.setPriority(30);

		this.registerReceiver(messageReceiver, filterMessage);
		this.registerReceiver(smsReceiver, filterSmsMessage);
	}

	@Override
	protected void onPause() {
		Timber.d("onPause called");
		this.unregisterReceiver(messageReceiver);
		this.unregisterReceiver(smsReceiver);
		super.onPause();
	}

	//--------------------------------------------------------------------------------------------------
	//RECEIVER -----------------------------------------------------------------------------------------

	private class MessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Handler MAIN_THREAD = new Handler(Looper.getMainLooper());
			final Message sendMessage = Parser.parseMessage(intent);
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

	private class SmsDynamicReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Timber.i("Dynamic sms Receiver");
			Handler MAIN_THREAD = new Handler(Looper.getMainLooper());
			final Message sendMessage = new Message(UUID.randomUUID().toString(), intent.getExtras().getString("message"), System.currentTimeMillis());
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

