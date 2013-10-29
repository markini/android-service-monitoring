package at.marki.Client.download;

import android.content.Context;
import android.os.AsyncTask;
import at.marki.Client.events.newMessageEvent;
import at.marki.Client.utils.Settingshandler;
import com.github.kevinsawicki.http.HttpRequest;
import com.squareup.otto.Bus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marki on 29.10.13.
 */
public class GetNewDataAsyncTask extends AsyncTask<Void, String, String> {

    private Bus bus;
    private Context context;

    public GetNewDataAsyncTask(Bus bus, Context context){
        this.bus = bus;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            String url = Settingshandler.getDownloadAddress(context);
            HttpRequest request = HttpRequest.get(url).connectTimeout(30000).readTimeout(30000);

            if (request.ok()) {
                JSONObject jsonObject = new JSONObject(request.body());
                String message = jsonObject.getString("message");
                return message;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String message) {
        bus.post(new newMessageEvent(message));
    }

}
