package at.marki.Client.monitoring;

import android.content.Context;
import at.marki.Client.utils.Settingshandler;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by marki on 06.11.13.
 */
public class PingGcm {
    public static boolean performPing(Context context) {
        try {
            String url = Settingshandler.getGcmCheckAddress(context);
            HttpRequest request = HttpRequest.get(url).connectTimeout(30000).readTimeout(30000);

            if (request.ok()) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
