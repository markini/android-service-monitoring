package at.marki.Server.Servlet;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by marki on 27.10.13.
 */
public class GCMSend {

    private final static Logger LOGGER = Logger.getLogger(GCMSend.class.getName());

    public static boolean sendMessage(String messageString, List<String> devices) {
        try {
            for (String deviceId : devices) {
                LOGGER.log(Level.INFO, "Sending to ID: " + deviceId);
            }
            LOGGER.log(Level.INFO, "message: " + messageString);
            Sender sender = new Sender("AIzaSyCxjJeypI3jZmBIQKoEUgCyGXF98lLZEMw");
            Message message = new Message.Builder().addData("message", messageString).build();
            MulticastResult results = sender.send(message, devices, 5);
            results.getResults();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
