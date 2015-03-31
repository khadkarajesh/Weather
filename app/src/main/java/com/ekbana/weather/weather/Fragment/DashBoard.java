package com.ekbana.weather.weather.Fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.adapter.PinnedTweetsAdapter;
import com.ekbana.weather.weather.adapter.TweetsAdapter;
import com.ekbana.weather.weather.adapter.WeatherAdapter;
import com.ekbana.weather.weather.common.Communicator;
import com.ekbana.weather.weather.common.CustomScrollView;
import com.ekbana.weather.weather.common.Utils;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.*;
import com.ekbana.weather.weather.model.PinnedTweet;
import com.ekbana.weather.weather.parser.Parser;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoard extends android.support.v4.app.Fragment implements CustomScrollView.ScrollViewListener {

    ViewPager weatherViewPager, tweetsViewPager, pinnedTweetsViewPager;
    CirclePageIndicator circlePageIndicator;
    DatabaseHelper databaseHelper;
    WeatherAdapter weatherAdapter;
    View view;


    AQuery aQuery;
    Parser parser;
    HashMap<String, String> param;
    String url = "http://uat.ekbana.info/social/api_getweather.php";
    String URL_TWEET = "http://uat.ekbana.info/social/api_gettweets.php";
    String URL_PINNED_TWEET = "http://uat.ekbana.info/social/api_getpinned_tweet.php";
    ArrayList<Weather> weatherList;
    HashMap<Integer, ArrayList<Weather.DaysWeather>> hashMap;


    HashMap<Integer, ArrayList<PinnedTweet>> hashMapTweet;
    HashMap<Integer, ArrayList<com.ekbana.weather.weather.model.PinnedTweet>> hashMapPinnedTweet;
    ArrayList<String> userHandle;
    ArrayList<String> pinnedUserHandle;
    ArrayList<String> lastDateOfTweet;
    ArrayList<String> lastDateOfLatestTweet;
    ArrayList<String> locationIdList;
    ArrayList<String> allLocationIdList;


    ArrayList<WeatherCurrent>listWeatherCurrents;
    HashMap<Integer,ArrayList<WeatherForecast>>hashMapWeatherForecast;

    Communicator communicator;
    CustomScrollView scroll;

    public DashBoard() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_dash_board, container, false);


        databaseHelper = new DatabaseHelper(getActivity());
        getWeatherData();
        getTweetData();
        getPinnedData();

        scroll = (CustomScrollView) view.findViewById(R.id.scroll);
        scroll.setScrollViewListener(this);


        return view;
    }

    public void getWeatherData() {

        locationIdList = databaseHelper.getLocationId();
        Log.d("max",""+locationIdList.size());
       /* if(Utils.isOnline(getActivity())) {
            allLocationIdList=databaseHelper.getAllLocationId();
            String id = "";
            for (int i = 0; i < allLocationIdList.size(); i++) {
                if (i <= allLocationIdList.size() - 2) {
                    id = id + allLocationIdList.get(i) + ",";
                } else {
                    id = id + allLocationIdList.get(i);
                }
            }

            HashMap<String, String> locationIdHash = new HashMap<>();
            locationIdHash.put("id", id);
            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("loading data. please wait....");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            aQuery = new AQuery(getActivity());
            aQuery.ajax(url, locationIdHash, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    super.callback(url, object, status);
                    if (object != null) {

                        try {

                            if(object.getJSONObject("1").getJSONArray("forecast").length()>0) {
                                databaseHelper.dropTableForWeather();
                                databaseHelper.createTableForWeather();
                                databaseHelper.insertWeatherCurrent(Parser.parseCurrentTemperature(object, locationIdList));
                                databaseHelper.insertWeatherForecast(Parser.parseWeeklyForeCast(object, locationIdList));
                                databaseHelper.insertWeatherHourlyForecast(Parser.parseHourlyForecast(object, locationIdList));
                                listWeatherCurrents = databaseHelper.getWeatherCurrent(locationIdList);
                                hashMapWeatherForecast = databaseHelper.getWeatherForecast(locationIdList);
                                callWeatherAdapter();
                                Log.d("","");
                            }
                            else
                            {
                                Log.d("Null:","Null");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("NullE:","Null");
                    }
                }
            });
        }
        else {*/

            listWeatherCurrents = databaseHelper.getWeatherCurrent(locationIdList);
            hashMapWeatherForecast = databaseHelper.getWeatherForecast(locationIdList);
            callWeatherAdapter();
       /* }*/
    }

    public void callWeatherAdapter() {
        weatherViewPager = (ViewPager) view.findViewById(R.id.viewPagerWeather);
        weatherAdapter = new WeatherAdapter(getActivity(),listWeatherCurrents,hashMapWeatherForecast);
        weatherViewPager.setAdapter(weatherAdapter);
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.weatherIndicator);
        circlePageIndicator.setViewPager(weatherViewPager);
    }

    public void getTweetData() {
        parser = new Parser();
        userHandle = databaseHelper.getAllHandle();

        lastDateOfLatestTweet = databaseHelper.getLastPinnedTweetDate(userHandle);


        if (Utils.isOnline(getActivity())) {
            getTweetDataFromWeb();
        } else {
            hashMapTweet = databaseHelper.getDataSelectedHandle(userHandle);
            callTweetAdapter();
        }
    }

    public void getTweetDataFromWeb() {
        String handle = "";
        for (int i = 0; i < userHandle.size(); i++) {
            if (i <= userHandle.size() - 2) {
                handle = handle + userHandle.get(i) + ",";
            } else {
                handle = handle + userHandle.get(i);
            }
        }


        HashMap<String, String> handleHash = new HashMap<>();
        handleHash.put("handle", handle);
        aQuery = new AQuery(getActivity());
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("loading data. please wait....");
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        aQuery.progress(dialog).ajax(URL_TWEET, handleHash, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                if (object != null) {

                    try {
                        ArrayList<PinnedTweet> tweets = Parser.getUpdatedLatestTweet(object, userHandle, lastDateOfLatestTweet);
                        databaseHelper.insertUpdatedPinnedTweet(tweets);
                        //Log.d("hashMapTweet size:",""+hashMapTweet.size());
                        hashMapTweet = databaseHelper.getDataSelectedHandle(userHandle);
                        callTweetAdapter();

                        callTweetAdapter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void callTweetAdapter() {
        tweetsViewPager = (ViewPager) view.findViewById(R.id.viewPagerTweets);
        TweetsAdapter tweetsAdapter = new TweetsAdapter(getActivity(), userHandle, hashMapTweet);
        tweetsViewPager.setAdapter(tweetsAdapter);

        CirclePageIndicator circlePageIndicatorTweets = (CirclePageIndicator) view.findViewById(R.id.tweetsIndicator);
        circlePageIndicatorTweets.setViewPager(tweetsViewPager);
    }

    public void getPinnedData() {
        pinnedUserHandle = databaseHelper.getPinnedHandle();
        lastDateOfTweet = databaseHelper.getLastPinnedTweetDate(pinnedUserHandle);
        if (Utils.isOnline(getActivity())) {
            getPinnedDataFromWeb();
        } else {
            hashMapPinnedTweet = databaseHelper.getDataSelectedHandle(pinnedUserHandle);
            callPinnedAdapter();
        }

    }


    public void callPinnedAdapter() {
        pinnedTweetsViewPager = (ViewPager) view.findViewById(R.id.viewPagerPinnedTweets);
        PinnedTweetsAdapter pinnedTweetsAdapter = new PinnedTweetsAdapter(getActivity(), hashMapPinnedTweet);
        pinnedTweetsViewPager.setAdapter(pinnedTweetsAdapter);


    }

    public void getPinnedDataFromWeb() {
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
        aQuery = new AQuery(getActivity());
        aQuery.ajax(URL_PINNED_TWEET, handleHash, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                if (object != null) {

                    try {
                        ArrayList<PinnedTweet> tweets = Parser.getLatestPinnedTweet(object, pinnedUserHandle, lastDateOfTweet);
                        databaseHelper.insertUpdatedPinnedTweet(tweets);
                        hashMapPinnedTweet = databaseHelper.getDataSelectedHandle(pinnedUserHandle);
                        callPinnedAdapter();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        communicator.listenScroll(scrollView, x, y, oldx, oldy);

    }
}
