package at.marki.Client.events;

/**
 * Created by marki on 29.10.13.
 */
public class newMessageEvent {

    public final String message;

    public newMessageEvent(String message) {
        this.message = message;
    }
}
