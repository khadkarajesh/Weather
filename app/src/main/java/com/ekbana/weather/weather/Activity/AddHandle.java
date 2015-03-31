package com.ekbana.weather.weather.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekbana.weather.weather.Fragment.AddHandleDialog;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.adapter.CustomHandleAdapter;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.Tweet;

import java.util.ArrayList;

public class AddHandle extends ActionBarActivity {
//manas shrestha's comment
    Toolbar toolbar;
    ListView listView;
    ArrayList<Tweet> userDataHandle;
    CustomHandleAdapter handleAdapter;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_handle);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        databaseHelper=new DatabaseHelper(this);

        userDataHandle=databaseHelper.getAllTweet();


        listView= (ListView) findViewById(R.id.listViewHandle);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                {


                    Toast.makeText(AddHandle.this, "item at :" + position, Toast.LENGTH_LONG).show();
                    userDataHandle.remove(position);
                    TextView textView= (TextView) view.findViewById(R.id.userHandleName);

                    databaseHelper.deleteAllDataOfUserHandle(textView.getText().toString().substring(1));
                    new DatabaseHelper(getApplicationContext()).removeHandle(textView.getText().toString().substring(1));

                    handleAdapter.notifyDataSetChanged();


                }
            }
        });
        handleAdapter=new CustomHandleAdapter(getApplicationContext(),userDataHandle);
        listView.setAdapter(handleAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_handle, menu);
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
        if(id==R.id.addHandle)
        {
            AddHandleDialog fragmentDialog=new AddHandleDialog();
            fragmentDialog.show(getFragmentManager(),"show");

        }

        return super.onOptionsItemSelected(item);
    }
}
