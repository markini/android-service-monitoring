package at.marki.Client.monitoring;

import android.content.Context;
import at.marki.Client.utils.Settingshandler;
import com.github.kevinsawicki.http.HttpRequest;
import timber.log.Timber;

/**
 * Created by marki on 06.11.13.
 */
class PingServer {
    public static boolean performPing(Context context) {
        try {
            Timber.d("MONITOR PING SERVER!!!");
            String url = Settingshandler.getPingServerAddress(context);
            HttpRequest request = HttpRequest.get(url).connectTimeout(30000).readTimeout(30000);

            if (request.ok()) {
                return true;
            } else{
                return false;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
