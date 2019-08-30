package com.monstercode.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.DEFAULT_KEYS_SEARCH_LOCAL;

public class MainActivity extends AppCompatActivity {
    public ListView contacts_listview;
    public static final String TAG = "mainactivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreate: starting activity main");
        setContentView(R.layout.activity_main);
        contacts_listview = findViewById(R.id.contacts_listview);

        String[] projection = {
          ContactsContract.Columns.CONTACTS_NAME,
          ContactsContract.Columns.CONTACTS_CONTACT
        };

        String[] args = {"se"};
        String selection = ContactsContract.Columns.CONTACTS_NAME + " LIKE ?";
        Cursor cursor = getContentResolver().query(ContactsContract.CONTENT_URI,
                null,
                null,
                    null,
                ContactsContract.Columns.CONTACTS_NAME
        );

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                cursor,
                projection,
                new int[] {android.R.id.text1, android.R.id.text2}
                );

        contacts_listview.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_search) {
            onSearchRequested();
        }
        return super.onOptionsItemSelected(item);
    }
}
