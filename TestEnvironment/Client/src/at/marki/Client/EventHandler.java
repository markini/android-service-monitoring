package at.marki.Client;

import android.content.Context;
import at.marki.Client.monitoring.ApplicationState;
import at.marki.Client.utils.Settingshandler;

/**
 * Created by marki on 26.12.13.
 */
public class EventHandler {

	public static synchronized void calculateApplicationEvent(Context context){

		// CONNECTIVITY ------------------------------------------------------
		boolean connectivityState = Settingshandler.getConnectivityState(context);

		if(!connectivityState){ //no internet
			Settingshandler.setApplicationState(context, ApplicationState.TYPE_SMS);
			return;
		}

		// SERVER -------------------------------------------------------------
		boolean serverState = Settingshandler.getServerState(context);

		if(!serverState){ //no internet
			Settingshandler.setApplicationState(context, ApplicationState.TYPE_SMS);
			return;
		}

		// GCM -------------------------------------------------------------
		boolean gcmState = Settingshandler.getGcmState(context);

		if(!gcmState){ //no internet
			Settingshandler.setApplicationState(context, ApplicationState.TYPE_HTTP);
			return;
		}

		Settingshandler.setApplicationState(context, ApplicationState.TYPE_DEFAULT);

	}
}
