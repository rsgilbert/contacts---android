package com.monstercode.contacts;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;


/**
 * Provider for the Contacts app. This is the only class that knows about {@link AppDatabase}
 */
public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";
    private AppDatabase mOpenHelper;
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int CONTACTS = 100;
    private static final int CONTACTS_ID = 101;


    static final String CONTENT_AUTHORITY = "com.monstercode.contacts.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);



    private static UriMatcher buildUriMatcher() {
        Log.d(TAG, "going through app provider's buildUriMatcher");
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        Log.d(TAG, CONTENT_AUTHORITY);

        // eg content://com.monstercode.contacts.provider/contacts/2
        matcher.addURI(CONTENT_AUTHORITY, ContactsContract.TABLE_NAME + "/#", CONTACTS_ID);

        // eg content://com.monstercode.contacts.provider/contacts
        matcher.addURI(CONTENT_AUTHORITY, ContactsContract.TABLE_NAME, CONTACTS);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return false;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query called with URI " + uri);
        final int match = sUriMatcher.match(uri); // tells us what uri was passed into query

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch(match) {
            case CONTACTS:
                queryBuilder.setTables(ContactsContract.TABLE_NAME);
                break;
            case CONTACTS_ID:
                queryBuilder.setTables(ContactsContract.TABLE_NAME);
                long contactId = ContactsContract.getContactId(uri);
                queryBuilder.appendWhere(ContactsContract.Columns._ID + " = " + contactId);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }
        SQLiteDatabase sqLiteDatabase = mOpenHelper.getReadableDatabase();
        return queryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return ContactsContract.CONTENT_TYPE;
            case CONTACTS_ID:
                return ContactsContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "the insertion match uri is " + match);

        final SQLiteDatabase sqLiteDatabase;
        Uri returnUri = null;
        long recordId;

        switch (match) {
            case CONTACTS:
                sqLiteDatabase = mOpenHelper.getWritableDatabase(); // declare inside switch bse operation is slow
                recordId = sqLiteDatabase.insert(ContactsContract.TABLE_NAME, null, contentValues);

                if(recordId > 0) {
                    returnUri = ContactsContract.buildContactUri(recordId);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);

        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match uri is " + match);

        final SQLiteDatabase sqLiteDatabase;
        int count;

        String selectionCriteria; // for the where clause

        switch(match) {
            case CONTACTS:
                sqLiteDatabase = mOpenHelper.getWritableDatabase();
                count = sqLiteDatabase.delete(ContactsContract.TABLE_NAME, selection, selectionArgs);
                break;

            case CONTACTS_ID:
                sqLiteDatabase = mOpenHelper.getWritableDatabase();
                long contactsId = ContactsContract.getContactId(uri);
                selectionCriteria = ContactsContract.Columns._ID + " = " + contactsId;

                if(selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = sqLiteDatabase.delete(ContactsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }

        Log.d(TAG, "Delete finished, deleted " + count + " rows");
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match uri is " + match);

        final SQLiteDatabase sqLiteDatabase;
        int count;

        String selectionCriteria; // for the where clause

        switch(match) {
            case CONTACTS:
                sqLiteDatabase = mOpenHelper.getWritableDatabase();
                count = sqLiteDatabase.update(ContactsContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case CONTACTS_ID:
                sqLiteDatabase = mOpenHelper.getWritableDatabase();
                long contactsId = ContactsContract.getContactId(uri);
                selectionCriteria = ContactsContract.Columns._ID + " = " + contactsId;

                if(selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = sqLiteDatabase.update(ContactsContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }

        Log.d(TAG, "Update finished, updated " + count + " rows");
        return count;
    }


}
