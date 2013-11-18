package at.marki.Client.utils;

/**
 * Created by marki on 18.11.13.
 */
public class State {
    private final String name;

    private State(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static final State OK = new State("ok");
    public static final State SERVER_DOWN = new State("server down");
    public static final State GCM_DOWN = new State("gcm down");
}
