package at.marki.Server.Data;

/**
 * Created by marki on 14.11.13.
 */
public class Message {
    public final String id;
    public final String message;

    public Message(String id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
