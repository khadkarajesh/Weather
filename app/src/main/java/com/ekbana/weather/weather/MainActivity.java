package com.ekbana.weather.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ekbana.weather.weather.Fragment.DashBoard;
import com.ekbana.weather.weather.Fragment.LatestTweet;
import com.ekbana.weather.weather.Fragment.PinnedTweet;
import com.ekbana.weather.weather.Fragment.WeatherDetail;
import com.ekbana.weather.weather.common.Communicator;
import com.ekbana.weather.weather.common.CustomScrollView;
import com.ekbana.weather.weather.common.Utils;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.Weather;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements Communicator {
    FrameLayout body;
    Toolbar toolbar;
    DashBoard dashBoard;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment fragment;

    DatabaseHelper databaseHelper;
    CustomScrollView scrollView;
    RelativeLayout bottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //body = (FrameLayout) findViewById(R.id.body);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        dashBoard = new DashBoard();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.body, dashBoard);
        transaction.commit();
        databaseHelper=new DatabaseHelper(getApplicationContext());

        bottomMenu= (RelativeLayout) findViewById(R.id.bottomMenu);
//        scrollView= (CustomScrollView) findViewById(R.id.body);
//        scrollView.setScrollViewListener((CustomScrollView.ScrollViewListener) MainActivity.this);







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == R.id.refresh) {
            Toast.makeText(this, "Refresh is selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.addNew) {
           /* Toast.makeText(this,"addNew is selected",Toast.LENGTH_SHORT).show();
            return true;*/
            // startActivity(new Intent(MainActivity.this, AddAll.class));

            fragmentManager.beginTransaction().replace(R.id.body, new com.ekbana.weather.weather.Fragment.AddAll()).addToBackStack("addAll").commit();

        }


        return super.onOptionsItemSelected(item);
    }

    public void clickHandle(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        try {

            viewUnSelect();
            int id = view.getId();
            switch (id) {
                case R.id.dashboard:
                    view.setSelected(true);
                    toolbar.setTitle("Dashboard");
                    fragment = new DashBoard();
                    break;
                case R.id.tweet:
                    view.setSelected(true);
                    toolbar.setTitle("Tweet");
                    fragment = new LatestTweet();
                    break;
                case R.id.pinned:
                    view.setSelected(true);
                    toolbar.setTitle("Pinned Tweet");
                    fragment=new PinnedTweet();
                    break;
                case R.id.weather:
                    view.setSelected(true);
                    toolbar.setTitle("Weather");
                    fragment=new WeatherDetail();
                    break;

            }
            fragmentTransaction.replace(R.id.body, fragment);
            fragmentTransaction.commit();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }


//    @Override
//    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
////        // We take the last son in the scrollview
////        Log.d("data:of height",""+scrollView.getChildAt(0).getHeight());
////        Log.d("height of scrollY",""+scrollView.getScrollY());
////        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
////        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
//
//
//        if(scrollView.getScrollY()>(scrollView.getChildAt(0).getHeight()/5))
//        {
//            bottomMenu.setVisibility(View.INVISIBLE);
//        }
//        else if(scrollView.getScrollY()<scrollView.getChildAt(0).getHeight()/5)
//        {
//            bottomMenu.setVisibility(View.VISIBLE);
//        }
//    }
    public void viewUnSelect()
    {
        findViewById(R.id.dashboard).setSelected(false);
        findViewById(R.id.tweet).setSelected(false);
        findViewById(R.id.pinned).setSelected(false);
        findViewById(R.id.weather).setSelected(false);
    }

    @Override
    public void listenScroll(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView.getScrollY()>(scrollView.getChildAt(0).getHeight()/5))
        {
            bottomMenu.setVisibility(View.INVISIBLE);
        }
        else if(scrollView.getScrollY()<scrollView.getChildAt(0).getHeight()/5)
        {
            bottomMenu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack("addAll",FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
