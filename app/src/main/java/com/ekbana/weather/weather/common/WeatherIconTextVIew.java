package com.ekbana.weather.weather.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 3/31/15.
 */
public class WeatherIconTextVIew extends TextView {

    Context context;
    public WeatherIconTextVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }
    public void init()
    {
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"iconvault_forecastfont.ttf");
        setTypeface(typeface);
    }
}
