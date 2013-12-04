package at.marki.Client;

import android.app.Application;
import android.content.Context;
import at.marki.Client.dialogs.DeleteDialog;
import at.marki.Client.download.GetNewDataService;
import at.marki.Client.monitoring.MonitorConnectivity;
import at.marki.Client.monitoring.MonitorServerPing;
import at.marki.ServiceMonitoring.Monitor;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import timber.log.Timber;

import javax.inject.Singleton;

public class ClientApplication extends Application {

	private ObjectGraph objectGraph;

	public static Monitor gcmCheckMonitor;
	public static Monitor connectivityMonitor;
	public static Monitor serverMonitor;

	@Override
	public void onCreate() {
		super.onCreate();
		objectGraph = ObjectGraph.create(new MainModule(this));
		Timber.plant(new Timber.DebugTree());
		//Data.createMockMessages(this);

//        if (gcmCheckMonitor == null) {
//            gcmCheckMonitor = new MonitorGcmCheck();
//        }
		if (connectivityMonitor == null) {
			connectivityMonitor = new MonitorConnectivity();
		}
		if (serverMonitor == null) {
			serverMonitor = new MonitorServerPing();
		}

		//startMonitoring();
	}

	public void startMonitoring() {
//        if (!gcmCheckMonitor.isRunning()) {
//            gcmCheckMonitor.executeMonitoring(this, false, 2);
//        }
		if (!connectivityMonitor.isRunning()) {
			connectivityMonitor.executeMonitoring(this, true, 2);
		}
		if (!serverMonitor.isRunning()) {
			serverMonitor.executeMonitoring(this, true, 3);
		}

	}

	public void inject(Object object) {
		objectGraph.inject(object);
	}

	@Module(entryPoints = {
			MainActivity.class, //
			FragmentMain.class, //
			DeleteDialog.class, //
			GetNewDataService.class //
	})
	static class MainModule {
		private final Context appContext;

		MainModule(Context appContext) {
			this.appContext = appContext;
		}

		@Provides
		@Singleton
		Bus provideBus() {
			return new Bus();
		}
	}
}
