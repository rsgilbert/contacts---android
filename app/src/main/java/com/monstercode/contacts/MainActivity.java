package com.monstercode.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView contacts_listview;
    public static final String TAG = "mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts_listview = findViewById(R.id.contacts_listview);

        String[] projection = {
          ContactsContract.Columns.CONTACTS_NAME,
          ContactsContract.Columns.CONTACTS_CONTACT,
          ContactsContract.Columns.CONTACTS_POSITION,
          ContactsContract.Columns.CONTACTS_MINISTRY
        };

        ContentResolver contentResolver = getContentResolver();

        ContentValues  contentValues = new ContentValues();
        contentValues.put(ContactsContract.Columns.CONTACTS_NAME, "Kato Lubwama");
        contentValues.put(ContactsContract.Columns.CONTACTS_MINISTRY, "Ministry of Labour");
        contentValues.put(ContactsContract.Columns.CONTACTS_CONTACT, "0703127648");
        contentValues.put(ContactsContract.Columns.CONTACTS_POSITION, "MP");

        // insert row
        Uri uri = contentResolver.insert(ContactsContract.CONTENT_URI, contentValues);


        contentValues.put(ContactsContract.Columns.CONTACTS_NAME, "Sewanyana");
        contentValues.put(ContactsContract.Columns.CONTACTS_MINISTRY, "Kyandondo");
        contentValues.put(ContactsContract.Columns.CONTACTS_CONTACT, "0755438902");
        contentValues.put(ContactsContract.Columns.CONTACTS_POSITION, "MP");

        // insert row
        uri = contentResolver.insert(ContactsContract.CONTENT_URI, contentValues);

     contentValues = new ContentValues();
        contentValues.put(ContactsContract.Columns.CONTACTS_NAME, "Geoffrey Sempawo");
        contentValues.put(ContactsContract.Columns.CONTACTS_MINISTRY, "Ministry of Energy");
        contentValues.put(ContactsContract.Columns.CONTACTS_CONTACT, "0772327648");
        contentValues.put(ContactsContract.Columns.CONTACTS_POSITION, "General Secretary");

        // insert row
        uri = contentResolver.insert(ContactsContract.CONTENT_URI, contentValues);
        Log.d(TAG, "oncreate: new entry uri: " + uri);

        contentValues.put(ContactsContract.Columns.CONTACTS_NAME, "Sewanyana");
        contentValues.put(ContactsContract.Columns.CONTACTS_MINISTRY, "Kyandondo");
        contentValues.put(ContactsContract.Columns.CONTACTS_CONTACT, "0755438902");
        contentValues.put(ContactsContract.Columns.CONTACTS_POSITION, "MP");

        // insert row
        uri = contentResolver.insert(ContactsContract.CONTENT_URI, contentValues);

        contentValues = new ContentValues();
        contentValues.put(ContactsContract.Columns.CONTACTS_NAME, "Musa Mawugwe");
        contentValues.put(ContactsContract.Columns.CONTACTS_MINISTRY, "KCCA");
        contentValues.put(ContactsContract.Columns.CONTACTS_CONTACT, "0753211238");
        contentValues.put(ContactsContract.Columns.CONTACTS_POSITION, "Kampala Resident Officer");

        // insert row
        uri = contentResolver.insert(ContactsContract.CONTENT_URI, contentValues);
        Log.d(TAG, "oncreate: new entry uri: " + uri);

        Cursor cursor = contentResolver.query(ContactsContract.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Columns.CONTACTS_NAME);

        if (cursor != null) {
            List<String> contact_names = new ArrayList<>();
            List<String> contact_contacts = new ArrayList<>();
            while (cursor.moveToNext()) {
                contact_names.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_NAME)));
                contact_contacts.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_CONTACT)));

            }
            cursor.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.contact_detail, R.id.contact_item, contact_names);
            contacts_listview.setAdapter(adapter);
        }
    }
}

// \