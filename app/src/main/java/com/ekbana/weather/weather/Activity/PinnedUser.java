package com.ekbana.weather.weather.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.adapter.PinnedUserActivityAdapter;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.PinnedTweet;

import java.util.ArrayList;

public class PinnedUser extends ActionBarActivity {

    ListView listView;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_user);
        databaseHelper=new DatabaseHelper(this);
        listView= (ListView) findViewById(R.id.pinnedUserListView);
        listView.setAdapter(new PinnedUserActivityAdapter(this, databaseHelper.getAllPinnedDataForActivity()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pinned_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
