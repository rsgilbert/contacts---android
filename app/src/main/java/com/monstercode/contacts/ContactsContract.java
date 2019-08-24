package com.monstercode.contacts;



/**
 *  Structure of the database, includes definitions for the column fields
 *  and the unique identifiers for records and the table as a whole
 */

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.monstercode.contacts.AppProvider.CONTENT_AUTHORITY_URI;
import static com.monstercode.contacts.AppProvider.CONTENT_AUTHORITY;

public class ContactsContract {
    static final String TABLE_NAME = "contacts";

    // contacts fields
    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String CONTACTS_NAME = "name";
        public static final String CONTACTS_CONTACT = "contact";
        public static final String CONTACTS_PICTURE = "picture";
        public static final String CONTACTS_MINISTRY = "ministry";
        public static final String CONTACTS_POSITION = "position";

        private Columns(){
            // keep it private to prevent instantiation
        }
    }

    // uri to access Contacts
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vmd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vmd.android.cursor.item/vnd" + CONTENT_AUTHORITY + "." + TABLE_NAME;


    static Uri buildContactUri(long contactId) {
        return ContentUris.withAppendedId(CONTENT_URI, contactId);
    }

    static long getContactId (Uri uri) { return ContentUris.parseId(uri); }
}

