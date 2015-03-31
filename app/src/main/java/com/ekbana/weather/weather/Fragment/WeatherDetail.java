package com.ekbana.weather.weather.Fragment;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.adapter.CustomWeatherDetailPagerAdapter;
import com.ekbana.weather.weather.common.BlurMaker;
import com.ekbana.weather.weather.common.Communicator;
import com.ekbana.weather.weather.common.CustomScrollView;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.WeatherCurrent;
import com.ekbana.weather.weather.model.WeatherForecast;
import com.ekbana.weather.weather.model.WeatherForecastHourly;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetail extends android.support.v4.app.Fragment implements CustomScrollView.ScrollViewListener {

    View view;
    ViewPager viewPager;
    Communicator communicator;
    DatabaseHelper databaseHelper;


    ArrayList<String> locationIdList;
    ArrayList<WeatherCurrent> listWeatherCurrents;
    HashMap<Integer, ArrayList<WeatherForecast>> hashMapWeatherForecast;
    HashMap<Integer, ArrayList<WeatherForecastHourly>> hashMapForecastHourly;

    public WeatherDetail() {
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
        view = inflater.inflate(R.layout.fragment_detail, container, false);


        viewPager = (ViewPager) view.findViewById(R.id.viewPager);


        Bitmap unBlurredBitmap = BlurMaker.decodeSampledBitmapFromResource(getResources(), getResources().
                getIdentifier("weather", "drawable", getActivity().getPackageName()), 10, 10);
        Bitmap blurredBitmap = BlurMaker.performBlur(unBlurredBitmap, viewPager, getActivity().getApplicationContext());


        databaseHelper=new DatabaseHelper(getActivity());
        locationIdList = databaseHelper.getLocationId();
        listWeatherCurrents = databaseHelper.getWeatherCurrent(locationIdList);
        hashMapWeatherForecast = databaseHelper.getWeatherForecast(locationIdList);
        hashMapForecastHourly = databaseHelper.getWeatherForecastHourly(locationIdList);
        Log.d("sizeofFH",""+hashMapForecastHourly.size());


        viewPager.setBackground(new BitmapDrawable(getResources(), blurredBitmap));
        viewPager.setAdapter(new CustomWeatherDetailPagerAdapter(getActivity().getApplicationContext(), listWeatherCurrents, hashMapWeatherForecast, hashMapForecastHourly));

        return view;
    }


    @Override
    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        communicator.listenScroll(scrollView, x, y, oldx, oldy);

    }
}
