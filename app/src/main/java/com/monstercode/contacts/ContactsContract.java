package com.monstercode.contacts;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

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
    public static final Uri CONTE_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vmd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vmd.android.cursor.item/vnd" + CONTENT_AUTHORITY + "." + TABLE_NAME;


    static Uri buildContactUri(long contactId) {
        return ContentUris.withAppendedId(CONTENT_URI, contactId);
    }

    static long getTaskId (Uri uri) { return ContentUris.parseId(uri); }
}

