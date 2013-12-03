package at.marki.Client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import at.marki.Client.utils.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marki on 03.12.13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "messageManager";

	// Table Names
	private static final String TABLE_MESSAGES = "table_messages";

	// Column names
	private static final String KEY_ID = "column_id";
	private static final String KEY_CREATED_AT = "column_created";
	private static final String KEY_MESSAGE = "column_message";


	// Table Create Statements
	private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE "
			+ TABLE_MESSAGES + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_MESSAGE
			+ " TEXT, " + KEY_CREATED_AT + " INTEGER)";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(CREATE_TABLE_MESSAGES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
		// create new tables
		onCreate(db);
	}

	public void createMessage(Message message) {

		if (messageExists(message.id)) {
			updateMessage(message);
			return;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		try {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, message.id);
			values.put(KEY_MESSAGE, message.message);
			values.put(KEY_CREATED_AT, message.date);

			// insert row
			db.insert(TABLE_MESSAGES, null, values);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public int updateMessage(Message message) {
		SQLiteDatabase db = this.getWritableDatabase();

		try {
			ContentValues values = new ContentValues();
			values.put(KEY_MESSAGE, message.message);

			// updating row
			int returnValue = db.update(TABLE_MESSAGES, values, KEY_ID + " = ?", new String[]{message.id});
			return returnValue;
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public void deleteMessage(Message message) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.delete(TABLE_MESSAGES, KEY_ID + " =?", new String[]{message.id});
		} catch (Exception e) {
			//do nothing
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public void clearAllMessages() {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL("delete from " + TABLE_MESSAGES);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

//    public List<Message> getMessages() {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        List<Message> messageList = new ArrayList<Message>();
//        // Select All Query
//        Cursor cursor = db.query(TABLE_MESSAGES, new String[]{KEY_ID}, null, null, null, null, null, null);
//
//        try {
//            if (cursor == null) {
//                return messageList;
//            }
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    try {
//                        Message message = getMessage(cursor.getString(0));
//                        if (message != null) {
//                            messageList.add(message);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } while (cursor.moveToNext());
//            }
//
//            return messageList;
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }

	public List<Message> getMessages() {
		List<Message> messageList = new ArrayList<Message>();
		String selectQuery = "SELECT * FROM " + TABLE_MESSAGES;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		try {
			// looping through all rows and adding messages
			if (cursor.moveToFirst()) {
				do {
					Message message = new Message(cursor.getString(cursor.getColumnIndex(KEY_ID)), cursor.getString(cursor.getColumnIndex(KEY_MESSAGE)), cursor.getLong(cursor.getColumnIndex(KEY_CREATED_AT)));
					messageList.add(message);
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if(db != null){
				db.close();
			}
		}

		return messageList;
	}

	public boolean messageExists(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MESSAGES, new String[]{KEY_ID}, KEY_ID + "=?", new String[]{id}, null, null,
				null, null);
		try {
			if (cursor == null || !cursor.moveToFirst()) {
				return false;
			} else {
				return true;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
	}

	public Message getMessage(String messageId) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MESSAGES, new String[]{KEY_MESSAGE, KEY_CREATED_AT}, KEY_ID + "=?", new String[]{messageId},
				null, null, null, null);
		try {
			if (cursor == null || !cursor.moveToFirst()) {
				return null;
			}

			// return project

			return new Message(messageId, cursor.getString(0), Long.parseLong(cursor.getString(1)));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}