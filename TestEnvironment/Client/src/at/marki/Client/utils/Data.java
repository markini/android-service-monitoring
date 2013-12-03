package at.marki.Client.utils;

import android.content.Context;
import at.marki.Client.database.DatabaseHelper;

import java.util.List;

/**
 * Created by marki on 30.10.13.
 */
public class Data {
    private static  List<Message> messages;
    private static DatabaseHelper databaseHelper;

    public static void updateMessage(Context context, Message message) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        databaseHelper.updateMessage(message);
    }

    public static void addMessage(Context context, Message message) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        databaseHelper.createMessage(message);
    }

    public static void deleteMessage(Context context, Message message) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        databaseHelper.deleteMessage(message);
    }

    public static List<Message> getMessages(Context context){
        if(messages == null){
            if (databaseHelper == null) {
                databaseHelper = new DatabaseHelper(context);
            }
            messages = databaseHelper.getMessages();
        }
        return messages;
    }
}
