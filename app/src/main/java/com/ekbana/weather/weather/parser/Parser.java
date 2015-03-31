package com.ekbana.weather.weather.parser;

import android.content.Context;
import android.util.Log;

import com.ekbana.weather.weather.model.PinnedTweet;
import com.ekbana.weather.weather.model.Tweet;
import com.ekbana.weather.weather.model.Weather;
import com.ekbana.weather.weather.model.WeatherCurrent;
import com.ekbana.weather.weather.model.WeatherForecast;
import com.ekbana.weather.weather.model.WeatherForecastHourly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by root on 2/24/15.
 */
public class Parser {

    public static ArrayList<Weather> getLocationTemp(Context context, JSONObject object, ArrayList<String> locationId) throws JSONException {

        Weather weather;
        ArrayList<Weather> weatherList = new ArrayList<>();
        for (int i = 0; i < object.length(); i++) {
            weather = new Weather();
            weather.location = object.getJSONObject(locationId.get(i)).getJSONObject("current").getString("location").split(",")[0];
            weather.temp = object.getJSONObject(locationId.get(i)).getJSONObject("current").getString("temp");
            weatherList.add(weather);

        }
        return weatherList;
    }

    public static HashMap<Integer, ArrayList<Weather.DaysWeather>> getDaysWeather(Context context, JSONObject object, ArrayList<String> locationId) throws JSONException {
        HashMap<Integer, ArrayList<Weather.DaysWeather>> hashMap = new HashMap<>();
        Weather weather = new Weather();

        for (int j = 0; j < object.length(); j++) {
            ArrayList<Weather.DaysWeather> daysWeatherList = new ArrayList<>();
            for (int i = 0; i < object.getJSONObject(locationId.get(j)).getJSONArray("forcast").length(); i++) {

                Weather.DaysWeather daysWeather = weather.new DaysWeather();
                daysWeather.day = object.getJSONObject(locationId.get(j)).getJSONArray("forcast").getJSONObject(i).getString("day").substring(0, 3);
                // Log.d("day:",""+daysWeather.day);
                daysWeather.low = object.getJSONObject(locationId.get(j)).getJSONArray("forcast").getJSONObject(i).getString("low");
                daysWeather.high = object.getJSONObject(locationId.get(j)).getJSONArray("forcast").getJSONObject(i).getString("high");
                daysWeather.condition = object.getJSONObject(locationId.get(j)).getJSONArray("forcast").getJSONObject(i).getString("condition");
                daysWeatherList.add(daysWeather);
            }
            hashMap.put(j, daysWeatherList);
        }
        return hashMap;
    }


    /*
     * parses the location of weather from url="http://uat.ekbana.info/social/api_weatherlocation.php".
     * @param JsonArray
     * returns arrayList of weather object.
     */

    public static ArrayList<Weather> getLocationFirstTime(JSONArray jsonArray) throws JSONException {
        Weather weather;
        ArrayList<Weather> nameList = new ArrayList<>();
        JSONObject jsonObject;
        int i;
        for (i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            weather = new Weather();
            weather.locationName = jsonObject.optString("title");
            //weather.status = Integer.parseInt(jsonObject.optString("status"));
            weather.location_id = Integer.parseInt(jsonObject.optString("id"));
            nameList.add(weather);
        }
        return nameList;
    }

    public HashMap<Integer, ArrayList<Tweet>> getTweet(JSONObject object, ArrayList<String> userHandle) throws JSONException {
        HashMap<Integer, ArrayList<Tweet>> hashMap = new HashMap<>();
        ArrayList<Tweet> tweetList;
        Tweet tweet;
        for (int i = 0; i < object.length(); i++) {
            tweetList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray(userHandle.get(i));
            for (int j = 0; j < jsonArray.length(); j++) {
                tweet = new Tweet();
                tweet.url = jsonArray.getJSONObject(j).getString("user_profile_pic");
                tweet.userName = jsonArray.getJSONObject(j).getString("user_display_name");
                tweet.date = jsonArray.getJSONObject(j).getString("date");
                tweet.text = jsonArray.getJSONObject(j).getString("tweet");
                tweetList.add(tweet);
            }
            hashMap.put(i, tweetList);
        }
        return hashMap;
    }

    /*
     * parser to fetch the pinned user
      * */
    public static ArrayList<PinnedTweet> getPinnedUser(JSONArray jsonArray) throws JSONException {

        PinnedTweet pinnedTweet;
        ArrayList<PinnedTweet> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            pinnedTweet = new PinnedTweet();
            pinnedTweet.handle = jsonArray.getJSONObject(i).optString("handle");
            pinnedTweet.userName = jsonArray.getJSONObject(i).optString("displayname");
            pinnedTweet.imageUrl = jsonArray.getJSONObject(i).optString("profile_image");
            list.add(pinnedTweet);
        }
        return list;

    }
    /* parser to parse the data for the short layout
    * */


    public static HashMap<Integer, ArrayList<PinnedTweet>> getPinnedTweet(JSONObject object, ArrayList<String> userHandle) throws JSONException {


        /******** test   ********/

        HashMap<Integer, ArrayList<PinnedTweet>> hashMap = new HashMap<>();
        ArrayList<PinnedTweet> tweetList;
        PinnedTweet tweet;
        int k = 0;
        for (int i = 0; i < object.length(); i++) {

            tweetList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray(userHandle.get(i));

            if (jsonArray.length() > 0) {


                for (int j = 0; j < jsonArray.length(); j++) {
                    tweet = new PinnedTweet();
                    tweet.url = jsonArray.getJSONObject(j).getString("user_profile_pic");
                    tweet.userName = jsonArray.getJSONObject(j).getString("user_display_name");
                    tweet.date = jsonArray.getJSONObject(j).getString("date_tweet");
                    tweet.text = jsonArray.getJSONObject(j).getString("tweet");
                    tweet.userHandle = jsonArray.getJSONObject(j).getString("user_handle");
                    Log.d("pinned", "" + tweet.text);
                    tweetList.add(tweet);

                }
                Log.d("pinned tweetlist size", "" + tweetList.size());
                hashMap.put(k, tweetList);
                k++;
            }
        }

        return hashMap;
    }

    /**
     * check for updated data in server of pinnedUser .
     *
     * @param object
     * @param pinnedUserHandle
     * @param lastUpdatedDate
     * @return
     * @throws JSONException
     * @throws ParseException
     */

    public static ArrayList<PinnedTweet> getLatestPinnedTweet(JSONObject object, ArrayList<String> pinnedUserHandle, ArrayList<String> lastUpdatedDate) throws JSONException, ParseException {
        int i, j;
        ArrayList<PinnedTweet> tweetsList = new ArrayList<>();
        PinnedTweet tweets;
        for (i = 0; i < pinnedUserHandle.size(); i++) {
            if (object.getJSONArray(pinnedUserHandle.get(i)).length() > 0) {
                for (j = 0; j < object.getJSONArray(pinnedUserHandle.get(i)).length(); j++) {
                    if (compareTwoDate(object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("date_tweet"), lastUpdatedDate.get(i)) == 1) {
                        tweets = new PinnedTweet();
                        tweets.userName = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("user_display_name");
                        tweets.userHandle = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("user_handle");
                        tweets.date = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("date_tweet");
                        tweets.text = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("tweet");
                        tweetsList.add(tweets);

                    }

                }
            }
        }
        return tweetsList;

    }

    public static ArrayList<PinnedTweet> getUpdatedLatestTweet(JSONObject object, ArrayList<String> pinnedUserHandle, ArrayList<String> lastUpdatedDate) throws JSONException, ParseException {
        int i, j;
        ArrayList<PinnedTweet> tweetsList = new ArrayList<>();
        PinnedTweet tweets;
        for (i = 0; i < pinnedUserHandle.size(); i++) {
            if (object.getJSONArray(pinnedUserHandle.get(i)).length() > 0) {
                for (j = 0; j < object.getJSONArray(pinnedUserHandle.get(i)).length(); j++) {
                    if (compareTwoDate(object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("date"), lastUpdatedDate.get(i)) == 1) {
                        Log.d("date>" + object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("date"), "is>" + lastUpdatedDate.get(i));
                        tweets = new PinnedTweet();
                        tweets.userName = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("user_display_name");
                        tweets.userHandle = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("user_handle");
                        tweets.date = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("date");
                        tweets.text = object.getJSONArray(pinnedUserHandle.get(i)).getJSONObject(j).optString("tweet");
                        tweetsList.add(tweets);

                    }

                }
            }
        }
        return tweetsList;

    }


    /**
     * used to parse  url,username,userHandle for a single userName.
     *
     * @param userHandle
     * @param object
     * @return single Tweet object.
     * @throws JSONException
     */

    public static Tweet parseSingleHandle(String userHandle, JSONObject object) throws JSONException {

        Tweet tweet = new Tweet();
        tweet.userName = object.getJSONArray(userHandle).getJSONObject(0).optString("user_display_name");
        tweet.userHandle = object.getJSONArray(userHandle).getJSONObject(0).optString("user_handle");
        tweet.url = object.getJSONArray(userHandle).getJSONObject(0).optString("user_profile_pic");
        return tweet;
    }

    public static ArrayList<PinnedTweet> parseLatestTweetData(String userHandle, JSONObject object) throws JSONException {
        PinnedTweet tweet;
        ArrayList<PinnedTweet> tweetsList = new ArrayList<>();
        int i;
        if (object.getJSONArray(userHandle).length() > 0) {
            for (i = 0; i < object.getJSONArray(userHandle).length(); i++) {
                tweet = new PinnedTweet();
                tweet.userName = object.getJSONArray(userHandle).getJSONObject(i).getString("user_display_name");
                tweet.userHandle = object.getJSONArray(userHandle).getJSONObject(i).getString("user_handle");
                tweet.date = object.getJSONArray(userHandle).getJSONObject(i).getString("date");
                Log.d("fragmentDate:", "" + tweet.date);

                tweet.text = object.getJSONArray(userHandle).getJSONObject(i).getString("tweet");
                tweetsList.add(tweet);
            }
            return tweetsList;
        } else {
            Log.d("else", "");
            return tweetsList;
        }


    }


    /**
     * @param object
     * @param locationId
     * @return arrayList of type WeatherCurrent.
     * @throws JSONException
     */
    public static ArrayList<WeatherCurrent> parseCurrentTemperature(JSONObject object, ArrayList<String> locationId) throws JSONException {
        int i;
        WeatherCurrent weatherCurrent;
        ArrayList<WeatherCurrent> weatherCurrents = new ArrayList<>();
        for (i = 0; i < locationId.size(); i++) {
            weatherCurrent = new WeatherCurrent();
            weatherCurrent.locationId = locationId.get(i);
            weatherCurrent.locationName = object.getJSONObject(locationId.get(i)).getJSONObject("current").optString("location").split(",")[0];
            weatherCurrent.temp = object.getJSONObject(locationId.get(i)).getJSONObject("current").optString("temp");
            weatherCurrents.add(weatherCurrent);
        }
        return weatherCurrents;
    }

    public static ArrayList<WeatherForecast> parseWeeklyForeCast(JSONObject object, ArrayList<String> locationId) throws JSONException {

        Log.d("abc","");
        Log.d("size",""+locationId.size());
        int i, j;
        WeatherForecast weatherForecast;
        ArrayList<WeatherForecast> weatherForecasts = new ArrayList<>();

        for (i = 0; i < locationId.size(); i++) {
            for (j = 0; j < object.getJSONObject(locationId.get(i)).getJSONArray("forcast").length(); j++) {
                weatherForecast = new WeatherForecast();
                weatherForecast.locationId = locationId.get(i);
                weatherForecast.day = object.getJSONObject(locationId.get(i)).getJSONArray("forcast").getJSONObject(j).optString("day").substring(0, 3);
                Log.d("forecast:","day"+weatherForecast.day);
                weatherForecast.high = object.getJSONObject(locationId.get(i)).getJSONArray("forcast").getJSONObject(j).optString("high");
                weatherForecast.low = object.getJSONObject(locationId.get(i)).getJSONArray("forcast").getJSONObject(j).optString("low");
                weatherForecast.condition = object.getJSONObject(locationId.get(i)).getJSONArray("forcast").getJSONObject(j).optString("condition");
                weatherForecast.icon = object.getJSONObject(locationId.get(i)).getJSONArray("forcast").getJSONObject(j).optString("icon");
                weatherForecasts.add(weatherForecast);
            }
        }
        Log.d("RKK",""+weatherForecasts.size());
        return weatherForecasts;
    }

    public static ArrayList<WeatherForecastHourly> parseHourlyForecast(JSONObject object,ArrayList<String> locationId) throws JSONException {
        int i,j;
        WeatherForecastHourly hourly;
        ArrayList<WeatherForecastHourly>hourlies=new ArrayList<>();
        for(i=0; i<locationId.size(); i++)
        {
            for(j=0; j< object.getJSONObject(locationId.get(i)).getJSONArray("forcasthourly").length(); j++)
            {
                hourly=new WeatherForecastHourly();
                hourly.locationId=locationId.get(i);
                hourly.time=object.getJSONObject(locationId.get(i)).getJSONArray("forcasthourly").getJSONObject(i).optString("time");
                hourly.temperature=object.getJSONObject(locationId.get(i)).getJSONArray("forcasthourly").getJSONObject(i).optString("temperature");
                hourly.conditionTitle=object.getJSONObject(locationId.get(i)).getJSONArray("forcasthourly").getJSONObject(i).optString("condition_title");
                hourly.conditionIcon=object.getJSONObject(locationId.get(i)).getJSONArray("forcasthourly").getJSONObject(i).optString("condition_icon");
                hourly.temperatureFeelsLike=object.getJSONObject(locationId.get(i)).getJSONArray("forcasthourly").getJSONObject(i).optString("temprature_feelslike");
                hourly.pop=object.getJSONObject(locationId.get(i)).getJSONArray("forcasthourly").getJSONObject(i).optString("pop");
                hourlies.add(hourly);
            }
        }
        return  hourlies;
    }

    public static int compareTwoDate(String dateOne, String dateTwo) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date parseDateOne = dateFormat.parse(dateOne);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date pDateTwo = dateFormat2.parse(dateTwo);
        if (parseDateOne.compareTo(pDateTwo) == -1) {
            System.out.println("date one is less than date two");
            return -1;
        } else if (parseDateOne.compareTo(pDateTwo) == 0) {

            System.out.println("date one is equal date two");
            return 0;
        } else {
            System.out.println("date one is greater than date two");
            return 1;
        }


    }


}
