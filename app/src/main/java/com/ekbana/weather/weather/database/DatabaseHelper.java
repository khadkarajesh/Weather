package com.ekbana.weather.weather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ekbana.weather.weather.model.PinnedTweet;
import com.ekbana.weather.weather.model.Tweet;
import com.ekbana.weather.weather.model.Weather;
import com.ekbana.weather.weather.model.WeatherCurrent;
import com.ekbana.weather.weather.model.WeatherForecast;
import com.ekbana.weather.weather.model.WeatherForecastHourly;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 2/18/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;

    SQLiteDatabase sqLiteDatabase;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Weather";


    private static final String PLACE_TABLE_NAME = "tbl_place";
    private static final String CREATE_TABLE_PLACE = "CREATE TABLE IF NOT EXISTS " + PLACE_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,location VARCHAR(255),status INTEGER DEFAULT 1,location_id INTEGER)";
    private static final String DROP_TABLE_PLACE = "DROP TABLE IF EXISTS tbl_place";
    private static final String PLACE_LOCATION = "location";
    private static final String PLACE_STATUS = "status";
    private static final String PLACE_LOCATION_ID = "location_id";

    private static final String HANDLE_TABLE_NAME = "tbl_handle";
    private static final String HANDLE_TABLE_USERNAME = "username";
    private static final String HANDLE_TABLE_HANDLE_NAME = "handle";
    private static final String HANDLE_TABLE_HANDLE_IMAGE_URL = "url";

    private static final String CREATE_TABLE_HANDLE = "CREATE TABLE IF NOT EXISTS " + HANDLE_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(255),handle VARCHAR(255),url TEXT)";
    private static final String DROP_TABLE_HANDLE = "DROP TABLE IF EXISTS tbl_handle";

    private static final String PINNED_TABLE_NAME = "tbl_pinned";
    private static final String PINNED_TABLE_USERNAME = "username";
    private static final String PINNED_TABLE_HANDLE = "handle";
    private static final String PINNED_TABLE_IMAGE_URL = "url";
    private static final String PINNED_TABLE_STATUS = "status";

    private static final String CREATE_PINNED_TABLE = "CREATE TABLE IF NOT EXISTS " + PINNED_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(255),handle VARCHAR(255),url TEXT,status INTEGER DEFAULT 1)";
    private static final String DROP_TABLE_PINNED = "DROP TABLE IF EXISTS tbl_pinned";


    /**
     * **create table to store the whole tweets  with timestamp*****
     */
    private static final String TWEETER_TABLE_NAME = "tbl_twitter";
    private static final String TWEETER_COLUMN_ID = "_id";
    private static final String TWEETER_COLUMN_HANDLE = "handle";
    private static final String TWEETER_COLUMN_USERNAME = "username";
    private static final String TWEETER_COLUMN_IMAGE_URL = "url";
    private static final String TWEETER_COLUMN_TWEET = "tweet";
    private static final String TWEETER_COLUMN_DATE = "date";

    private static final String CREATE_TABLE_TWITTER = "CREATE TABLE IF NOT EXISTS " + TWEETER_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,handle VARCHAR(255),username VARCHAR(255),tweet TEXT,date VARCHAR(255))";
    private static final String DROP_TABLE_TWITTER = "DROP TABLE IF EXISTS tbl_twitter";


    /**
     * tbl_temp_current.
     * for the storage of temperature data.
     */

    private static final String TEMPERATURE_CURRENT_TABLE = "tbl_temp_current";
    private static final String TEMPERATURE_CURRENT_TABLE_COLUMN_LOCATION_ID = "locationId";
    private static final String TEMPERATURE_CURRENT_TABLE_COLUMN_TEMPERATURE = "temp";
    private static final String TEMPERATURE_CURRENT_TABLE_COLUMN_LOCATION_NAME = "locationName";

    private static final String CREATE_TABLE_TEMPERATURE_CURRENT = "CREATE TABLE IF NOT EXISTS " + TEMPERATURE_CURRENT_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,locationId VARCHAR(255),temp VARCHAR(255),locationName VARCHAR(255))";
    private static final String DROP_TABLE__TEMPERATURE_CURRENT = "DROP TABLE IF EXISTS " + TEMPERATURE_CURRENT_TABLE;


    private static final String TEMPERATURE_FORECAST_TABLE = "tbl_forecast";
    private static final String TEMPERATURE_FORECAST_TABLE_COLUMN_LOCATION_ID = "locationId";
    private static final String TEMPERATURE_FORECAST_TABLE_COLUMN_DAY = "day";
    private static final String TEMPERATURE_FORECAST_TABLE_COLUMN_HIGH_TEMPERATURE = "high";
    private static final String TEMPERATURE_FORECAST_TABLE_COLUMN_LOW_TEMPERATURE = "low";
    private static final String TEMPERATURE_FORECAST_TABLE_COLUMN_CONDITION = "condition";
    private static final String TEMPERATURE_FORECAST_TABLE_COLUMN_ICON = "icon";

    private static final String CREATE_TEMPERATURE_FORECAST_TABLE = "CREATE TABLE IF NOT EXISTS " + TEMPERATURE_FORECAST_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,locationId VARCHAR(255),day VARCHAR(255),high VARCHAR(255),low VARCHAR(255),condition VARCHAR(255),icon VARCHAR(255))";
    private static final String DROP_TEMPERATURE_FORECAST_TABLE = "DROP TABLE IF EXISTS " + TEMPERATURE_FORECAST_TABLE;


    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE = "tbl_forecast_hourly";
    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_LOCATION_ID = "locationId";
    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TIME = "time";
    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TEMPERATURE = "temperature";
    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_CONDITION_TITLE = "conditionTitle";
    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_CONDITION_ICON = "conditionIcon";
    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TEMPERATURE_FEELS_LIKE = "temperatureFeelsLike";
    private static final String TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_POP = "pop";

    private static final String CREATE_TEMPERATURE_FORECAST_HOURLY_TABLE = "CREATE TABLE IF NOT EXISTS " + TEMPERATURE_FORECAST_HOURLY_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,locationId VARCHAR(255),time VARCHAR(255),temperature VARCHAR(255),conditionTitle VARCHAR(255),conditionIcon VARCHAR(255),temperatureFeelsLike VARCHAR(255),pop VARCHAR(255))";
    private static final String DROP_TEMPERATURE_FORECAST_HOURLY_TABLE = "DROP TABLE IF EXISTS " + TEMPERATURE_FORECAST_HOURLY_TABLE;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLACE);
        db.execSQL(CREATE_TABLE_HANDLE);
        db.execSQL(CREATE_PINNED_TABLE);
        Toast.makeText(context, "database created", Toast.LENGTH_LONG).show();

    }

    public void dropPinnedTable() {
        getWritableDatabase().execSQL(DROP_TABLE_PINNED);
        Toast.makeText(context, "successfully dropped pinned table", Toast.LENGTH_LONG).show();

    }

    public void dropTweetTable() {
        getWritableDatabase().execSQL(DROP_TABLE_HANDLE);
        Toast.makeText(context, "successfully dropped latest tweet table", Toast.LENGTH_LONG).show();
    }

    public void createPinnedTable() {
        getWritableDatabase().execSQL(CREATE_PINNED_TABLE);
        Toast.makeText(context, "table pinned created ", Toast.LENGTH_LONG).show();
    }

    public void createHandleTable() {
        getWritableDatabase().execSQL(CREATE_TABLE_HANDLE);
        Toast.makeText(context, "database created", Toast.LENGTH_LONG).show();
    }

    public void createTwitterTable() {
        getWritableDatabase().execSQL(CREATE_TABLE_TWITTER);
        Toast.makeText(context, "Table twitter database is created", Toast.LENGTH_LONG).show();
    }
    public void createTableForWeather()
    {
        getWritableDatabase().execSQL(CREATE_TABLE_TEMPERATURE_CURRENT);
        getWritableDatabase().execSQL(CREATE_TEMPERATURE_FORECAST_TABLE);
        getWritableDatabase().execSQL(CREATE_TEMPERATURE_FORECAST_HOURLY_TABLE);
        Toast.makeText(context,"successfully created Tempreture table",Toast.LENGTH_LONG).show();
    }
    public void dropTableForWeather()
    {
        getWritableDatabase().execSQL(DROP_TABLE__TEMPERATURE_CURRENT);
        getWritableDatabase().execSQL(DROP_TEMPERATURE_FORECAST_TABLE);
        getWritableDatabase().execSQL(DROP_TEMPERATURE_FORECAST_HOURLY_TABLE);
        Toast.makeText(context, "successfully droped Tempreture table", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_PLACE);
        db.execSQL(DROP_TABLE_HANDLE);
        db.execSQL(DROP_TABLE_PINNED);

    }

    public void insertLocationData(ArrayList<Weather> weatherList) {
        sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues;
            int i;
            for (i = 0; i < weatherList.size(); i++) {
                contentValues = new ContentValues();
                contentValues.put(PLACE_LOCATION, weatherList.get(i).locationName);
                contentValues.put(PLACE_STATUS, weatherList.get(i).status);
                contentValues.put(PLACE_LOCATION_ID, weatherList.get(i).location_id);
                getWritableDatabase().insert(PLACE_TABLE_NAME, null, contentValues);

            }
            Log.d("successfully", "inserted location");
        } finally {
            sqLiteDatabase.close();
        }

    }

    public void updateLocationStatus(int id, int status) {
        sqLiteDatabase = getWritableDatabase();
        try {
            getWritableDatabase().execSQL("UPDATE tbl_place SET status=" + status + " WHERE _id=" + id);
        } finally {
            //sqLiteDatabase.close();
        }
    }

    public Cursor getAllLocationData() {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery("select * from tbl_place", null);
            return cursor;
        } finally {
//            cursor.close();
//            sqLiteDatabase.close();
        }

    }

    /*
    *comment

     */

    public ArrayList<Weather> getCheckedLocation() {
        sqLiteDatabase = getReadableDatabase();
        Cursor c = null;
        try {
            ArrayList<Weather> weatherList = new ArrayList<>();
            c = getReadableDatabase().rawQuery("select * from tbl_place where status=1", null);
            Weather weather;
            while (c.moveToNext()) {
                weather = new Weather();
                weather.location = c.getString(c.getColumnIndex("location"));
                weather.location_id = c.getInt(c.getColumnIndex("location_id"));
                weatherList.add(weather);

            }
            return weatherList;
        } finally {
//            c.close();
//            sqLiteDatabase.close();
        }
    }

    public ArrayList<String> getLocationId() {

        sqLiteDatabase = getReadableDatabase();
        Cursor c = null;
        try {
            ArrayList<String> locationList = new ArrayList<>();
            c = getReadableDatabase().rawQuery("select location_id from tbl_place where status=1", null);

            while (c.moveToNext()) {

                locationList.add(Integer.toString(c.getInt(c.getColumnIndex("location_id"))));


            }
            return locationList;
        } finally {

        }


    }

    public ArrayList<String> getAllLocationId() {
        sqLiteDatabase = getReadableDatabase();
        Cursor c = null;
        try {
            ArrayList<String> locationList = new ArrayList<>();
            c = getReadableDatabase().rawQuery("select * from tbl_place", null);

            while (c.moveToNext()) {

                locationList.add(Integer.toString(c.getInt(c.getColumnIndex("location_id"))));

            }
            return locationList;
        } finally {

        }
    }

    public void insertPinnedData(ArrayList<PinnedTweet> pinnedTweetList) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues;
        int i;
        try {
            for (i = 0; i < pinnedTweetList.size(); i++) {
                contentValues = new ContentValues();
                contentValues.put(PINNED_TABLE_USERNAME, pinnedTweetList.get(i).userName);
                Log.d("userName::" + i, "" + pinnedTweetList.get(i).userName);
                Log.d("url::" + i, "" + pinnedTweetList.get(i).imageUrl);
                contentValues.put(PINNED_TABLE_HANDLE, pinnedTweetList.get(i).handle);
                contentValues.put(PINNED_TABLE_IMAGE_URL, pinnedTweetList.get(i).imageUrl);
                getWritableDatabase().insert(PINNED_TABLE_NAME, null, contentValues);

            }

            Log.d("successfully", "inserted pinned");
        } finally {
            //sqLiteDatabase.close();
        }

    }

    public void updatePinnedStatus(int id, int status) {

        try {
            getWritableDatabase().execSQL("UPDATE tbl_pinned SET status=" + status + " WHERE _id=" + id);
            System.out.println("UPDATE tbl_pinned SET status=" + status + " WHERE _id=" + id);
            Toast.makeText(context, "suceesfully updated", Toast.LENGTH_SHORT).show();
        } finally {
            // sqLiteDatabase.close();

        }

    }

    /**
     * @return arrayList of pinned handle of status checked.
     */
    public ArrayList<String> getPinnedHandle() {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            ArrayList<String> list = new ArrayList<>();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_pinned WHERE status=1", null);

            while (cursor.moveToNext()) {

                list.add(cursor.getString(cursor.getColumnIndex("handle")));
            }
            return list;
        } finally {

        }
    }

    public ArrayList<String> getAllPinnedHandle() {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            ArrayList<String> list = new ArrayList<>();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_pinned", null);

            while (cursor.moveToNext()) {

                list.add(cursor.getString(cursor.getColumnIndex("handle")));
            }
            return list;
        } finally {

        }
    }

    public Cursor getAllPinnedData() {

        sqLiteDatabase = getReadableDatabase();
        try {
            return sqLiteDatabase.rawQuery("select * from tbl_pinned", null);

        } finally {
            //sqLiteDatabase.close();

        }

    }

    public ArrayList<PinnedTweet> getAllPinnedDataForActivity() {
        ArrayList<PinnedTweet> list = new ArrayList<>();
        PinnedTweet tweet;
        sqLiteDatabase = getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from tbl_pinned", null);
            while (cursor.moveToNext()) {
                tweet = new PinnedTweet();
                tweet.userName = cursor.getString(cursor.getColumnIndex(PINNED_TABLE_USERNAME));
                tweet.userHandle = cursor.getString(cursor.getColumnIndex(PINNED_TABLE_HANDLE));
                tweet.imageUrl = cursor.getString(cursor.getColumnIndex(PINNED_TABLE_IMAGE_URL));
                tweet.status = cursor.getInt(cursor.getColumnIndex(PINNED_TABLE_STATUS));
                list.add(tweet);

            }
        } finally {
            //sqLiteDatabase.close();

        }
        return list;

    }

    public void deleteAllPinnedData() {
        sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("delete  from tbl_pinned");
            Toast.makeText(context, "deleted all record", Toast.LENGTH_LONG).show();
        } finally {
            //sqLiteDatabase.close();
        }
    }


    public void addUserHandle(String userHandle)

    {
        sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(HANDLE_TABLE_USERNAME, userHandle);
            sqLiteDatabase.insert(HANDLE_TABLE_NAME, null, contentValues);

            Log.d("inserted:", "" + userHandle);
        } finally {
            sqLiteDatabase.close();

        }

    }

    public void addUserHandleObject(Tweet tweet) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HANDLE_TABLE_USERNAME, tweet.userName);
        contentValues.put(HANDLE_TABLE_HANDLE_NAME, tweet.userHandle);
        contentValues.put(HANDLE_TABLE_HANDLE_IMAGE_URL, tweet.url);
        sqLiteDatabase.insert(HANDLE_TABLE_NAME, null, contentValues);
        Toast.makeText(context, "handle added record", Toast.LENGTH_LONG).show();
    }

    public void removeHandle(String userHandle) {
        sqLiteDatabase = getWritableDatabase();
        try {
            String sql = "DELETE FROM " + HANDLE_TABLE_NAME + "  WHERE handle=" + "'" + userHandle + "'";
            Log.d("sql:", "" + sql);
            sqLiteDatabase.execSQL(sql);
        } finally {
            sqLiteDatabase.close();
        }
    }

    public ArrayList<String> getAllHandle() {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            ArrayList<String> list = new ArrayList<>();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + HANDLE_TABLE_NAME, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    list.add(cursor.getString(cursor.getColumnIndex("handle")));
                }
                return list;
            } else {
                return null;
            }
        } finally {
            sqLiteDatabase.close();
            cursor.close();
        }
    }

    public ArrayList<Tweet> getAllTweet() {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = null;
        Tweet tweet;
        ArrayList<Tweet> list = new ArrayList<>();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + HANDLE_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            tweet = new Tweet();
            tweet.userHandle = cursor.getString(cursor.getColumnIndex(HANDLE_TABLE_HANDLE_NAME));
            tweet.userName = cursor.getString(cursor.getColumnIndex(HANDLE_TABLE_USERNAME));
            tweet.url = cursor.getString(cursor.getColumnIndex(HANDLE_TABLE_HANDLE_IMAGE_URL));
            list.add(tweet);
        }
        return list;
    }


    /**
     * *****insert data to Twitter table with timestamp ********
     */

    public void insertDataToTweetTable(HashMap<Integer, ArrayList<PinnedTweet>> hashMapPinnedTweet) {
        int i, j;
        //Log.d("show log","");
        ContentValues contentValues = null;
        for (i = 0; i < hashMapPinnedTweet.size(); i++) {
            for (j = 0; j < hashMapPinnedTweet.get(i).size(); j++) {
                Log.d("log value", "" + hashMapPinnedTweet.get(i).get(j).userHandle);
                contentValues = new ContentValues();
                contentValues.put(TWEETER_COLUMN_HANDLE, hashMapPinnedTweet.get(i).get(j).userHandle);
                contentValues.put(TWEETER_COLUMN_USERNAME, hashMapPinnedTweet.get(i).get(j).userName);
                Log.d("username inserted", "" + j + hashMapPinnedTweet.get(i).get(j).userName);
                contentValues.put(TWEETER_COLUMN_TWEET, hashMapPinnedTweet.get(i).get(j).text);
                contentValues.put(TWEETER_COLUMN_DATE, hashMapPinnedTweet.get(i).get(j).date);
                getWritableDatabase().insert(TWEETER_TABLE_NAME, null, contentValues);
            }
        }

    }

    /**
     * @param pinnedTweetsList
     */
    public void insertUpdatedPinnedTweet(ArrayList<PinnedTweet> pinnedTweetsList) {
        sqLiteDatabase = getWritableDatabase();
        int i;
        ContentValues cv = null;
        for (i = 0; i < pinnedTweetsList.size(); i++) {
            cv = new ContentValues();
            cv.put(TWEETER_COLUMN_USERNAME, pinnedTweetsList.get(i).userName);
            cv.put(TWEETER_COLUMN_HANDLE, pinnedTweetsList.get(i).userHandle);
            cv.put(TWEETER_COLUMN_DATE, pinnedTweetsList.get(i).date);
            Log.d("updated data", "" + pinnedTweetsList.get(i).date);
            cv.put(TWEETER_COLUMN_TWEET, pinnedTweetsList.get(i).text);
            sqLiteDatabase.insert(TWEETER_TABLE_NAME, null, cv);

        }
    }

    /**
     * *** fetch data from tbl_tweeter for showing in layout ******
     */
    public HashMap<Integer, ArrayList<PinnedTweet>> getDataSelectedHandle(ArrayList<String> pinnedTweetHandle) {
        Cursor cursor = null;
        PinnedTweet tweet;
        int i;
        int k = 0;
        HashMap<Integer, ArrayList<PinnedTweet>> hashMapPinnedTweet = new HashMap<>();
        ArrayList<PinnedTweet> tweetsList;

        for (i = 0; i < pinnedTweetHandle.size(); i++) {
            cursor = getWritableDatabase().rawQuery("SELECT * FROM tbl_twitter where handle='" + pinnedTweetHandle.get(i) + "'" + "ORDER BY date DESC", null);
            cursor.getCount();
            tweetsList = new ArrayList<>();
            //Log.d("pull","");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    tweet = new PinnedTweet();
                    tweet.userHandle = cursor.getString(cursor.getColumnIndex(TWEETER_COLUMN_HANDLE));
                    tweet.userName = cursor.getString(cursor.getColumnIndex(TWEETER_COLUMN_USERNAME));
                    tweet.text = cursor.getString(cursor.getColumnIndex(TWEETER_COLUMN_TWEET));
                    tweet.date = cursor.getString(cursor.getColumnIndex(TWEETER_COLUMN_DATE));
                    Log.d("pull", "" + tweet.date);
                    tweetsList.add(tweet);

                }
                hashMapPinnedTweet.put(k, tweetsList);
                Log.d("kkkk" + k, "" + tweetsList.size());
                k++;
            }
        }
        return hashMapPinnedTweet;
    }

    /**
     * get image url of provided handle.
     *
     * @param handle
     * @return url of pinnedTweet user.
     */

    public String getImageUrlOfPinnedTweet(String handle) {

        Cursor cursor = null;
        String url = null;
        cursor = getWritableDatabase().rawQuery("SELECT * FROM tbl_pinned where handle='" + handle + "'", null);
        while (cursor.moveToNext()) {
            url = cursor.getString(cursor.getColumnIndex(PINNED_TABLE_IMAGE_URL));
        }
        return url;
    }

    public String getImageUrlOfLatestTweet(String handle) {
        Cursor cursor = null;
        String url = null;
        cursor = getWritableDatabase().rawQuery("SELECT * FROM tbl_handle where handle='" + handle + "'", null);
        while (cursor.moveToNext()) {
            url = cursor.getString(cursor.getColumnIndex(PINNED_TABLE_IMAGE_URL));
        }
        return url;
    }

    /**
     * method
     *
     * @param pinnedHandle
     */
    public ArrayList<String> getLastPinnedTweetDate(ArrayList<String> pinnedHandle) {

        sqLiteDatabase = getReadableDatabase();
        Cursor cursor;
        ArrayList<String> dateList = new ArrayList<>();
        int i;
        for (i = 0; i < pinnedHandle.size(); i++) {
            cursor = null;
            cursor = sqLiteDatabase.rawQuery("SELECT MAX(date) FROM tbl_twitter WHERE handle='" + pinnedHandle.get(i) + "'", null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    dateList.add(cursor.getString(0));
                    Log.d("maxDate:" + pinnedHandle.get(i), "" + cursor.getString(0));
                }
            }
        }
        return dateList;
    }

    /**
     * method used to insert data to Tweet table. arrayList of type PinnedTweet passed from
     * AddHandleDialog fragment.
     *
     * @param tweetsList
     */

    public void insertLatestTweet(ArrayList<PinnedTweet> tweetsList) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues cv;
        int i;
        for (i = 0; i < tweetsList.size(); i++) {
            cv = new ContentValues();
            cv.put(TWEETER_COLUMN_USERNAME, tweetsList.get(i).userName);
            cv.put(TWEETER_COLUMN_HANDLE, tweetsList.get(i).userHandle);
            Log.d("addUserHandle:date", "" + tweetsList.get(i).date);
            cv.put(TWEETER_COLUMN_DATE, tweetsList.get(i).date);
            cv.put(TWEETER_COLUMN_TWEET, tweetsList.get(i).text);
            sqLiteDatabase.insert(TWEETER_TABLE_NAME, null, cv);
        }
    }

    /**
     * deletes the all data of particular user from tweeter table.
     *
     * @param userHandle
     */
    public void deleteAllDataOfUserHandle(String userHandle) {
        sqLiteDatabase = getReadableDatabase();
        String sql = "DELETE FROM " + TWEETER_TABLE_NAME + "  WHERE handle=" + "'" + userHandle + "'";
        Log.d("deletedFrom", "Tweet" + sql);
        sqLiteDatabase.execSQL(sql);
    }

    public void insertWeatherCurrent(ArrayList<WeatherCurrent> weatherCurrents) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues;
        int i;
        for (i = 0; i < weatherCurrents.size(); i++) {
            contentValues = new ContentValues();
            contentValues.put(TEMPERATURE_CURRENT_TABLE_COLUMN_LOCATION_ID, weatherCurrents.get(i).locationId);
            contentValues.put(TEMPERATURE_CURRENT_TABLE_COLUMN_LOCATION_NAME, weatherCurrents.get(i).locationName);
            contentValues.put(TEMPERATURE_CURRENT_TABLE_COLUMN_TEMPERATURE, weatherCurrents.get(i).temp);
            sqLiteDatabase.insert(TEMPERATURE_CURRENT_TABLE, null, contentValues);
            Log.d("dbcurrent",""+weatherCurrents.get(i).temp);
        }
        Log.d("successfully inserted ", "Weather current");
    }

    public void insertWeatherForecast(ArrayList<WeatherForecast> weatherForecasts) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues;
        int i;
        for (i = 0; i < weatherForecasts.size(); i++) {

            contentValues = new ContentValues();
            contentValues.put(TEMPERATURE_FORECAST_TABLE_COLUMN_DAY, weatherForecasts.get(i).day);
            contentValues.put(TEMPERATURE_FORECAST_TABLE_COLUMN_CONDITION, weatherForecasts.get(i).condition);
            contentValues.put(TEMPERATURE_FORECAST_TABLE_COLUMN_HIGH_TEMPERATURE, weatherForecasts.get(i).high);
            contentValues.put(TEMPERATURE_FORECAST_TABLE_COLUMN_LOW_TEMPERATURE, weatherForecasts.get(i).low);
            contentValues.put(TEMPERATURE_FORECAST_TABLE_COLUMN_ICON, weatherForecasts.get(i).icon);
            contentValues.put(TEMPERATURE_FORECAST_TABLE_COLUMN_LOCATION_ID, weatherForecasts.get(i).locationId);
            sqLiteDatabase.insert(TEMPERATURE_FORECAST_TABLE, null, contentValues);
            Log.d("dbforecast"+i, "" + weatherForecasts.get(i).day);

        }
        Log.d("successfully inserted ","WeatherForecast");
    }

    public void insertWeatherHourlyForecast(ArrayList<WeatherForecastHourly> hourlies) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues;
        int i;
        for (i = 0; i < hourlies.size(); i++) {

            contentValues = new ContentValues();
            contentValues.put(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TIME, hourlies.get(i).time);
            contentValues.put(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TEMPERATURE, hourlies.get(i).temperature);
            contentValues.put(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_CONDITION_TITLE, hourlies.get(i).conditionTitle);
            contentValues.put(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_CONDITION_ICON, hourlies.get(i).conditionIcon);
            contentValues.put(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TEMPERATURE_FEELS_LIKE, hourlies.get(i).temperatureFeelsLike);
            contentValues.put(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_POP, hourlies.get(i).pop);
            contentValues.put(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_LOCATION_ID, hourlies.get(i).locationId);
            sqLiteDatabase.insert(TEMPERATURE_FORECAST_HOURLY_TABLE, null, contentValues);
            Log.d("dbhourlies" + i, "" + hourlies.get(i).time);
        }
        Log.d("successfully inserted ","WeatherHourlyForecast");

    }


    public ArrayList<WeatherCurrent> getWeatherCurrent(ArrayList<String> locationId) {
        int i, j;
        Cursor cursor;
        sqLiteDatabase = getReadableDatabase();
        WeatherCurrent weatherCurrent=null;

        ArrayList<WeatherCurrent> weatherCurrents=new ArrayList<>();
        for (i = 0; i < locationId.size(); i++) {
            cursor = null;
            cursor = sqLiteDatabase.rawQuery("select * from tbl_temp_current WHERE locationId='" + locationId.get(i) + "'", null);


            while (cursor.moveToNext()) {
                weatherCurrent = new WeatherCurrent();
                weatherCurrent.locationName = cursor.getString(cursor.getColumnIndex(TEMPERATURE_CURRENT_TABLE_COLUMN_LOCATION_NAME));
                weatherCurrent.temp = cursor.getString(cursor.getColumnIndex(TEMPERATURE_CURRENT_TABLE_COLUMN_TEMPERATURE));
                weatherCurrent.locationId = cursor.getString(cursor.getColumnIndex(TEMPERATURE_CURRENT_TABLE_COLUMN_LOCATION_ID));


            }
            weatherCurrents.add(weatherCurrent);
        }
        return weatherCurrents;
    }

    public HashMap<Integer, ArrayList<WeatherForecast>> getWeatherForecast(ArrayList<String> locationId) {
        int i, j;
        Cursor cursor;
        sqLiteDatabase = getReadableDatabase();
        WeatherForecast weatherForecast;
        HashMap<Integer, ArrayList<WeatherForecast>> hashMap = new HashMap<>();
        ArrayList<WeatherForecast> weatherForecasts;
        for (i = 0; i < locationId.size(); i++) {
            cursor = null;
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_forecast WHERE locationId='" + locationId.get(i) + "'", null);
            weatherForecasts = new ArrayList<>();
            while (cursor.moveToNext()) {

                weatherForecast = new WeatherForecast();
                weatherForecast.day = cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_TABLE_COLUMN_DAY));
                weatherForecast.high = cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_TABLE_COLUMN_HIGH_TEMPERATURE));
                weatherForecast.low = cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_TABLE_COLUMN_LOW_TEMPERATURE));
                weatherForecast.condition = cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_TABLE_COLUMN_CONDITION));
                weatherForecast.icon = cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_TABLE_COLUMN_ICON));
                weatherForecast.locationId = cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_TABLE_COLUMN_LOCATION_ID));
                weatherForecasts.add(weatherForecast);
            }
            hashMap.put(i, weatherForecasts);
        }
        return hashMap;
    }

    public HashMap<Integer,ArrayList<WeatherForecastHourly>> getWeatherForecastHourly(ArrayList<String> locationId)
    {
        int i;
        Cursor cursor;
        sqLiteDatabase = getReadableDatabase();
        WeatherForecastHourly hourly;
        ArrayList<WeatherForecastHourly>hourlies;
        HashMap<Integer,ArrayList<WeatherForecastHourly>>hashMap=new HashMap<>();
        for(i=0; i<locationId.size(); i++)
        {
            cursor=null;
            cursor= sqLiteDatabase.rawQuery("SELECT * FROM tbl_forecast_hourly WHERE locationId='" + locationId.get(i) + "'", null);
            hourlies=new ArrayList<>();
            while(cursor.moveToNext())
            {

                hourly=new WeatherForecastHourly();
                hourly.time=cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TIME));
                hourly.temperature=cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TEMPERATURE));
                hourly.conditionTitle=cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_CONDITION_TITLE));
                hourly.conditionIcon=cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_CONDITION_ICON));
                hourly.temperatureFeelsLike=cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_TEMPERATURE_FEELS_LIKE));
                hourly.pop=cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_POP));
                hourly.locationId=cursor.getString(cursor.getColumnIndex(TEMPERATURE_FORECAST_HOURLY_TABLE_COLUMN_LOCATION_ID));
                hourlies.add(hourly);
            }
            hashMap.put(i,hourlies);
        }
        return hashMap;
    }


    public void getDataBetween()
    {

        sqLiteDatabase=getReadableDatabase();
        //sqLiteDatabase.rawQuery("select ");
       // Cursor cursor="";

    }

}
