package com.ekbana.weather.weather.common;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekbana.weather.weather.R;

import java.util.zip.Inflater;

/**
 * Created by root on 2/16/15.
 */
public class WeatherDialog extends DialogFragment {

    String[]locationValue={"Kathmandu","Pokhara","Nepalgunj","Dhangadi","Biratnagar"};
    TextView location,checkBoxWeather;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.custom_weather_dialog_fragment,null);
        location= (TextView) view.findViewById(R.id.location);

        checkBoxWeather= (TextView) view.findViewById(R.id.checkBoxWeather);
        return view;
    }
}
