package com.monstercode.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class AppDatabase extends SQLiteOpenHelper {
    public static final String TAG = "AppDatabase";
    public static final String DATABASE_NAME = "contacts.db";
    public static final int DATABSE_VERSION = 1;

    private static AppDatabase instance = null;

    private AppDatabase(Context context) { // only one instance of db at time hence private
        super(context, DATABASE_NAME, null, DATABSE_VERSION);

    }

    /**
     * Get an instance of the app's singleton database helper object
     * @param context the content provider's context
     * @return a SQLite database helper object
     */

    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getinstance creating a new instance");
            instance = new AppDatabase(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql;
        sql = "CREATE TABLE " + ContactsContract.TABLE_NAME + " ("
        + ContactsContract.Columns._ID + " INTEGER PRIMARY KEY, "
        + ContactsContract.Columns.CONTACTS_NAME + " TEXT NOT NULL, "
        + ContactsContract.Columns.CONTACTS_CONTACT + " TEXT, "
        + ContactsContract.Columns.CONTACTS_MINISTRY + " TEXT, "
        + ContactsContract.Columns.CONTACTS_PICTURE + " TEXT, "
        + ContactsContract.Columns.CONTACTS_POSITION + " TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch(oldVersion) {
            case 1:
                // upgrade logic from version 1
                break;
            default:
                throw new IllegalArgumentException("onUpgrade() with unknown new version");
        }
    }
}
