package com.monstercode.contacts;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class SearchableActivity extends AppCompatActivity {
    public static final String TAG = "SearchableActivity";
    public ListView contacts_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts_listview = findViewById(R.id.contacts_listview);
        Log.d(TAG, "oncreate, starting search");

        // get intent, verify action, use query
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Log.d(TAG, "oncreate, making query" + query);

            Cursor matchesCursor = getMatches(query);

            ListAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.two_line_list_item,
                    matchesCursor,
                    new String[] { ContactsContract.Columns.CONTACTS_NAME,
                        ContactsContract.Columns.CONTACTS_CONTACT },
                    new int[] { android.R.id.text1, android.R.id.text2 }
            );
            contacts_listview.setAdapter(adapter);
        }
        // handling suggestions
        else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String selected_id = intent.getDataString();
            Log.d(TAG, "data string is " + selected_id);
            Uri uri = ContactsContract.buildContactUri(Integer.parseInt(selected_id));
            Intent i = new Intent(SearchableActivity.this, InfoActivity.class);
            i.setData(uri);
            startActivity(i);

        }
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
    public Cursor getMatches(String query) {
        String selection = ContactsContract.Columns.CONTACTS_NAME + " LIKE ? OR "
                + ContactsContract.Columns.CONTACTS_CONTACT + " LIKE ? "
                + ContactsContract.Columns.CONTACTS_POSITION + " MATCH ? OR "
                + ContactsContract.Columns.CONTACTS_MINISTRY + " MATCH ?";


        return getContentResolver().query(ContactsContract.CONTENT_URI,
                null,
                selection,
                    new String[]{ query },
                ContactsContract.Columns.CONTACTS_NAME);
    }
}
