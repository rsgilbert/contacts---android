package com.monstercode.contacts;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    public static final String TAG = "InfoActivity";
    TextView name, contact, position, ministry;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        name = findViewById(R.id.contact_name);
        contact = findViewById(R.id.contact_contact);
        position = findViewById(R.id.contact_position);
        ministry = findViewById(R.id.contact_ministry);

        String[] projection = {
                ContactsContract.Columns._ID,
                ContactsContract.Columns.CONTACTS_NAME,
                ContactsContract.Columns.CONTACTS_CONTACT,
                ContactsContract.Columns.CONTACTS_POSITION,
                ContactsContract.Columns.CONTACTS_MINISTRY
        };

        Intent intent = getIntent();
        Uri uri = intent.getData();

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(uri,
                projection,
                null,
                null,
                null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                name.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_NAME)));
                contact.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_CONTACT)));
                position.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_POSITION)));
                ministry.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Columns.CONTACTS_MINISTRY)));
            }
            cursor.close();
        }
    }
}
