package com.monstercode.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
          ContactsContract.Columns._ID,
          ContactsContract.Columns.CONTACTS_NAME,
          ContactsContract.Columns.CONTACTS_CONTACT,
          ContactsContract.Columns.CONTACTS_POSITION,
          ContactsContract.Columns.CONTACTS_MINISTRY
        };

        ContentResolver contentResolver = getContentResolver();


        // manipulating appDb
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Columns.CONTACTS_NAME, "Geofrey Ssenyondo");
        contentValues.put(ContactsContract.Columns.CONTACTS_CONTACT, "0733333333");
        contentValues.put(ContactsContract.Columns.CONTACTS_POSITION, "Chairman");
        contentValues.put(ContactsContract.Columns.CONTACTS_MINISTRY, "Ministry of Internal Affairs");

//        contentResolver.insert(ContactsContract.CONTENT_URI, contentValues);


        Cursor cursor = contentResolver.query(ContactsContract.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Columns.CONTACTS_NAME);

        if (cursor != null) {
            final List<String> contact_names = new ArrayList<>();
            final List<Integer> contact_ids = new ArrayList<>();
            while (cursor.moveToNext()) {
                contact_names.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_NAME)));
                contact_ids.add(cursor.getInt(cursor.getColumnIndex(ContactsContract.Columns._ID)));


            }
            cursor.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.contact_detail, R.id.contact_item, contact_names);
            contacts_listview.setAdapter(adapter);
            contacts_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int _id =  contact_ids.get(i);
                    Log.d(TAG, "contactlist item id: " + _id);
                    Uri uri = ContactsContract.buildContactUri(_id);
                    Log.d(TAG, "New uri is " + uri);
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
        }
    }
}

// \