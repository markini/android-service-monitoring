package at.marki.Client;

import android.app.Application;
import android.content.Context;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import timber.log.Timber;

import javax.inject.Singleton;

//@ReportsCrashes(formKey = "YOUR_FORM_KEY")
public class ClientApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        // ACRA.init(this);
        super.onCreate();
        objectGraph = ObjectGraph.create(new MainModule(this));
        Timber.plant(new Timber.DebugTree());
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    @Module(entryPoints = {
            MainActivity.class //
    })
    static class MainModule {
        private final Context appContext;

        MainModule(Context appContext) {
            this.appContext = appContext;
        }

//		@Provides
//		@Singleton
//		UploadTaskQueue provideUploadTaskQueue(Gson gson) {
//			return UploadTaskQueue.create(appContext, gson);
//		}

        @Provides
        @Singleton
        Bus provideBus() {
            return new Bus();
        }

//		@Provides
//		@Singleton
//		Gson provideGson() {
//			return new GsonBuilder().create();
//		}
    }
}
