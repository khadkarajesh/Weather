package com.ekbana.weather.weather.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 2/5/15.
 */
public class FontTextView extends TextView {
    Context context;
    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }
    public void init()
    {
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"weather.ttf");
        setTypeface(typeface);
    }
}
