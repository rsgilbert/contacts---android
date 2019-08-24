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


        Cursor cursor = contentResolver.query(ContactsContract.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Columns.CONTACTS_NAME);

        if (cursor != null) {
            List<String> contact_names = new ArrayList<>();
            while (cursor.moveToNext()) {
                contact_names.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_NAME)));

            }
            cursor.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.contact_detail, R.id.contact_item, contact_names);
            contacts_listview.setAdapter(adapter);
        }
    }
}

// \