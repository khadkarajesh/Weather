package com.ekbana.weather.weather.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ekbana.weather.weather.MainActivity;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.common.FileRead;
import com.ekbana.weather.weather.common.Utils;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.parser.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Splash extends ActionBarActivity {

    private String URL = "http://uat.ekbana.info/social/api_weatherlocation.php";
    private String URL_PINNED = "http://uat.ekbana.info/social/api_getpinnedusers.php";
    private String URL_FETCH_PINNED_TWEET = "http://uat.ekbana.info/social/api_getpinned_tweet.php";

    private String URL_GET_WEATHER = "http://uat.ekbana.info/social/api_getweather.php";
    DatabaseHelper databaseHelper;
    ProgressBar progressBar;


    ArrayList<String> pinnedUserHandle;
    HashMap<Integer, ArrayList<com.ekbana.weather.weather.model.PinnedTweet>> hashMapPinnedTweet;
    AQuery aQuery;


    ArrayList<String> locationIdList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(false);
        databaseHelper = new DatabaseHelper(this);
        if (Utils.doesDatabaseExist(Splash.this, "Weather")) {

            //databaseHelper.createTwitterTable();
            //fetchPinnedData();
            finish();
            startActivity(new Intent(Splash.this, MainActivity.class));
           /* databaseHelper.dropTableForWeather();
            databaseHelper.createTableForWeather();
            fetchWeatherData();*/

//            databaseHelper.dropPinnedTable();
//            databaseHelper.createPinnedTable();
//            fetchPinnedUser();
            //deleteDatabase("Weather");
        } else {
            //deleteDatabase("Weather");
            databaseHelper.getWritableDatabase();
            fetchData();
            fetchPinnedUser();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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

    public void fetchData() {
        AQuery aQuery = new AQuery(this);

        aQuery.progress(R.id.progressBar).ajax(URL, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray object, AjaxStatus status) {
                super.callback(url, object, status);

                try {
                    if (object != null) {
                        databaseHelper.insertLocationData(Parser.getLocationFirstTime(object));
                        //finish();
                        //startActivity(new Intent(Splash.this,MainActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void fetchPinnedUser() {
        AQuery aQuery = new AQuery(this);
        aQuery.ajax(URL_PINNED, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray object, AjaxStatus status) {
                super.callback(url, object, status);
                try {
                    if (object != null) {
                        Log.d("pinned user info:", "" + object);
                        databaseHelper.insertPinnedData(Parser.getPinnedUser(object));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * * fetch the data of pinned user and save data to twitter table for offline content showing ****
     */

    public void fetchPinnedData() {
        pinnedUserHandle = databaseHelper.getAllPinnedHandle();
        String handle = "";
        for (int i = 0; i < pinnedUserHandle.size(); i++) {
            if (i <= pinnedUserHandle.size() - 2) {
                handle = handle + pinnedUserHandle.get(i) + ",";
            } else {
                handle = handle + pinnedUserHandle.get(i);
            }
        }

        HashMap<String, String> handleHash = new HashMap<>();
        handleHash.put("handle", handle);
        aQuery = new AQuery(this);
        aQuery.ajax(URL_FETCH_PINNED_TWEET, handleHash, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                if (object != null) {
                    try {
                        hashMapPinnedTweet = Parser.getPinnedTweet(object, pinnedUserHandle);
                        //Log.d("pinned size:",""+hashMapPinnedTweet.size());
                        databaseHelper.insertDataToTweetTable(hashMapPinnedTweet);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void fetchWeatherData() {
        locationIdList = databaseHelper.getAllLocationId();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(FileRead.loadJSONFromAsset(Splash.this));
            databaseHelper.insertWeatherCurrent(Parser.parseCurrentTemperature(jsonObject, locationIdList));
            databaseHelper.insertWeatherForecast(Parser.parseWeeklyForeCast(jsonObject, locationIdList));
            databaseHelper.insertWeatherHourlyForecast(Parser.parseHourlyForecast(jsonObject, locationIdList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d("object", "" + jsonObject);


       /* String id = "";
        for (int i = 0; i < locationIdList.size(); i++) {
            if (i <= locationIdList.size() - 2) {
                id = id + locationIdList.get(i) + ",";
            } else {
                id = id + locationIdList.get(i);
            }
        }
        final HashMap<String, String> locationIdHash = new HashMap<>();
        locationIdHash.put("id", id);
        ProgressDialog dialog = new ProgressDialog(Splash.this);
        dialog.setMessage("loading data. please wait....");
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        aQuery = new AQuery(Splash.this);
        aQuery.ajax(URL_GET_WEATHER, locationIdHash, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                if (object != null) {

                    try {

                        JSONObject jsonObject = new JSONObject(FileRead.loadJSONFromAsset(Splash.this));
                        //Log.d("object", "" + jsonObject);

                        databaseHelper.insertWeatherCurrent(Parser.parseCurrentTemperature(jsonObject, locationIdList));
                        databaseHelper.insertWeatherForecast(Parser.parseWeeklyForeCast(jsonObject, locationIdList));
                        databaseHelper.insertWeatherHourlyForecast(Parser.parseHourlyForecast(jsonObject, locationIdList));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        });
*/

    }
}
