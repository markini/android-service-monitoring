package at.marki.Client.events;

import at.marki.Client.utils.Message;

/**
 * Created by marki on 29.10.13.
 */
public class newMessageEvent {

    public final Message message;

    public newMessageEvent(Message message) {
        this.message = message;
    }
}
