package at.marki.Client;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.google.android.gcm.GCMRegistrar;
import timber.log.Timber;

public class MainActivity extends Activity {

    public static final String TAG_MAIN_FRAGMENT = "at.marki.client.fragment.main.tag";
    public static final String TAG_PREFS_FRAGMENT = "at.marki.client.fragment.prefs.tag";

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        manageGCM();
        startTransaction(R.id.fragment_frame, new FragmentMain(), TAG_MAIN_FRAGMENT, false);
    }

    public void manageGCM() {
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
//            Intent intent = new Intent(this, RegisterGcmIdService.class);
//            this.startService(intent);
        }
    }

    public void startTransaction(int id, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(id, fragment, tag);
        if (addToBackStack) {
            fragTransaction.addToBackStack(null);
        }
        fragTransaction.commit();
    }
}
