package at.marki.Client;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gcm.GCMRegistrar;
import de.akquinet.android.androlog.Log;

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        manageGCM();
    }

    public void manageGCM() {
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("") || !GCMRegistrar.isRegistered(this)) {
            GCMRegistrar.register(this, GCMIntentService.SENDER_ID);
            Log.e(this, "received ID: " + GCMRegistrar.getRegistrationId(this));
        } else {
            if (BuildConfig.DEBUG) {
                Log.v(this, "Already registered");
            }
//            Intent intent = new Intent(this, RegisterGcmIdService.class);
//            this.startService(intent);
        }
    }
}
