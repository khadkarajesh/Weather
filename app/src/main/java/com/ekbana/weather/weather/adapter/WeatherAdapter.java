package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.model.Weather;
import com.ekbana.weather.weather.model.WeatherCurrent;
import com.ekbana.weather.weather.model.WeatherForecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 2/3/15.
 */
public class WeatherAdapter extends PagerAdapter {

    String IMAGE_BASE_URL="http://icons.wxug.com/i/c/k/";
    Context context;
    ArrayList<Weather> weatherList;
    HashMap<Integer, ArrayList<Weather.DaysWeather>> hashMap;
    LayoutInflater inflater;
    TextView location, temp, dayOne, dayTwo, dayThree, dayFour, dayFive, daySix,
            dayOneHigh, dayTwoHigh, dayThreeHigh, dayFourHigh, dayFiveHigh, daySixHigh,
            dayOneLow, dayTwoLow, dayThreeLow, dayFourLow, dayFiveLow, daySixLow;
    View view;

    TextView dayOneTV;
    ImageView dayOneImageView,dayTwoImageView,dayThreeImageView,dayFourImageView,dayFiveImageView,daySixImageView;
    ArrayList<WeatherCurrent> listWeatherCurrents;
    HashMap<Integer, ArrayList<WeatherForecast>> hashMapWeatherForecast;

    public WeatherAdapter(Context context, ArrayList<WeatherCurrent>hashMapWeatherCurrents,
                          HashMap<Integer, ArrayList<WeatherForecast>> hashMapWeatherForecast) {
        this.context = context;
        this.listWeatherCurrents=hashMapWeatherCurrents;
        this.hashMapWeatherForecast=hashMapWeatherForecast;

    }

    @Override
    public int getCount() {
        return listWeatherCurrents.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.weather_short_layout, null);

        location = (TextView) view.findViewById(R.id.address);
        temp = (TextView) view.findViewById(R.id.temp);

        dayOne = (TextView) view.findViewById(R.id.dayOne);
        dayTwo = (TextView) view.findViewById(R.id.dayTwo);
        dayThree = (TextView) view.findViewById(R.id.dayThree);
        dayFour = (TextView) view.findViewById(R.id.dayFour);
        dayFive = (TextView) view.findViewById(R.id.dayFive);
        daySix = (TextView) view.findViewById(R.id.daySix);

        dayOneHigh = (TextView) view.findViewById(R.id.dayOneHigh);
        dayTwoHigh = (TextView) view.findViewById(R.id.dayTwoHigh);
        dayThreeHigh = (TextView) view.findViewById(R.id.dayThreeHigh);
        dayFourHigh = (TextView) view.findViewById(R.id.dayFourHigh);
        dayFiveHigh = (TextView) view.findViewById(R.id.dayFiveHigh);
        daySixHigh = (TextView) view.findViewById(R.id.daySixHigh);

        dayOneLow = (TextView) view.findViewById(R.id.dayOneLow);
        dayTwoLow = (TextView) view.findViewById(R.id.dayTwoLow);
        dayThreeLow = (TextView) view.findViewById(R.id.dayThreeLow);
        dayFourLow = (TextView) view.findViewById(R.id.dayFourLow);
        dayFiveLow = (TextView) view.findViewById(R.id.dayFiveLow);
        daySixLow = (TextView) view.findViewById(R.id.daySixLow);

        dayOneImageView= (ImageView) view.findViewById(R.id.dayOneImageView);
        dayTwoImageView= (ImageView) view.findViewById(R.id.dayTwoImage);
        dayThreeImageView= (ImageView) view.findViewById(R.id.dayThreeImageView);
        dayFourImageView= (ImageView) view.findViewById(R.id.dayFourImageView);
        dayFiveImageView= (ImageView) view.findViewById(R.id.dayFiveImageVIew);
        daySixImageView= (ImageView) view.findViewById(R.id.daySixImageView);



        Log.d("lftA",""+hashMapWeatherForecast.get(position).get(0).day);
        dayOne.setText(hashMapWeatherForecast.get(position).get(0).day.toString());
        dayTwo.setText(hashMapWeatherForecast.get(position).get(1).day.toString());
        dayThree.setText(hashMapWeatherForecast.get(position).get(2).day.toString());
        dayFour.setText(hashMapWeatherForecast.get(position).get(3).day.toString());
        dayFive.setText(hashMapWeatherForecast.get(position).get(4).day.toString());
        daySix.setText(hashMapWeatherForecast.get(position).get(5).day.toString());

        dayOneHigh.setText(hashMapWeatherForecast.get(position).get(0).high.toString());
        dayTwoHigh.setText(hashMapWeatherForecast.get(position).get(1).high.toString());
        dayThreeHigh.setText(hashMapWeatherForecast.get(position).get(2).high.toString());
        dayFourHigh.setText(hashMapWeatherForecast.get(position).get(3).high.toString());
        dayFiveHigh.setText(hashMapWeatherForecast.get(position).get(4).high.toString());
        daySixHigh.setText(hashMapWeatherForecast.get(position).get(5).high.toString());

        dayOneLow.setText(hashMapWeatherForecast.get(position).get(0).low.toString());
        dayTwoLow.setText(hashMapWeatherForecast.get(position).get(1).low.toString());
        dayThreeLow.setText(hashMapWeatherForecast.get(position).get(2).low.toString());
        dayFourLow.setText(hashMapWeatherForecast.get(position).get(3).low.toString());
        dayFiveLow.setText(hashMapWeatherForecast.get(position).get(4).low.toString());
        daySixLow.setText(hashMapWeatherForecast.get(position).get(5).low.toString());


        Picasso.with(context).load(IMAGE_BASE_URL+hashMapWeatherForecast.get(position).get(0).icon).into(dayOneImageView);
        Picasso.with(context).load(IMAGE_BASE_URL + hashMapWeatherForecast.get(position).get(1).icon).into(dayTwoImageView);
        Picasso.with(context).load(IMAGE_BASE_URL+hashMapWeatherForecast.get(position).get(2).icon).into(dayThreeImageView);
        Picasso.with(context).load(IMAGE_BASE_URL + hashMapWeatherForecast.get(position).get(3).icon).into(dayFourImageView);
        Picasso.with(context).load(IMAGE_BASE_URL+hashMapWeatherForecast.get(position).get(4).icon).into(dayFiveImageView);
        Picasso.with(context).load(IMAGE_BASE_URL + hashMapWeatherForecast.get(position).get(5).icon).into(daySixImageView);


        location.setText(listWeatherCurrents.get(position).locationName);
        temp.setText(listWeatherCurrents.get(position).temp);

        container.addView(view);
        return view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
