package at.marki.Client;

import android.app.Application;
import android.content.Context;
import at.marki.Client.download.GetNewDataService;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import timber.log.Timber;

import javax.inject.Singleton;

public class ClientApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new MainModule(this));
        Timber.plant(new Timber.DebugTree());
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    @Module(entryPoints = {
            MainActivity.class, //
            FragmentMain.class, //
            GetNewDataService.class //
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
