package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.graph.BarChartItem;
import com.ekbana.weather.weather.graph.LineChartItem;
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
 * Created by root on 3/19/15.
 */
public class CustomWeatherDetailPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<WeatherCurrent> weatherCurrents;
    HashMap<Integer, ArrayList<WeatherForecast>> hashMapWeatherForecast;
    HashMap<Integer, ArrayList<WeatherForecastHourly>> hashMapForecastHourly;


    TextView locationName,conditionTitle,precipitation,date,time,temperature;

    public CustomWeatherDetailPagerAdapter(Context context, ArrayList<WeatherCurrent> weatherCurrents,
                                           HashMap<Integer, ArrayList<WeatherForecast>> hashMapWeatherForecast,
                                           HashMap<Integer, ArrayList<WeatherForecastHourly>> hashMapForecastHourly) {
        this.context = context;
        this.weatherCurrents=weatherCurrents;
        this.hashMapWeatherForecast=hashMapWeatherForecast;
        this.hashMapForecastHourly=hashMapForecastHourly;

    }

    @Override
    public int getCount() {
        return weatherCurrents.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_weather_detail, null);
        TextView locationNameD= (TextView) view.findViewById(R.id.locationNameD);
        locationNameD.setText(weatherCurrents.get(position).locationName);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.l);
        linearLayout.addView(new LineChartItem(generateDataLine(1), context).getView(0, null, context));
        linearLayout.addView(new BarChartItem(generateDataBar(1), context.getApplicationContext()).getView(0, null, context));


        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(0);
        data.add(4);
        data.add(2);
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(1);
        data.add(2);
        data.add(1);
        data.add(2);
        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            //e1.add(new Entry((int) (Math.random() * 65) + 40, i));
            e1.add(new Entry(data.get(i), i));
        }

        LineDataSet d1 = new LineDataSet(e1, "Temprature");
        d1.setLineWidth(3f);
        d1.setCircleSize(5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "Precipitation");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);
        d.setColor(context.getResources().getColor(R.color.barColor));

        BarData cd = new BarData(getHours(), d);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */


    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
//        m.add("Jan");
//        m.add("Feb");
//        m.add("Mar");
//        m.add("Apr");
//        m.add("May");
//        m.add("Jun");
//        m.add("Jul");
//        m.add("Aug");
//        m.add("Sep");
//        m.add("Oct");
//        m.add("Nov");
//        m.add("Dec");

        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");
        m.add("");

        return m;
    }

    private ArrayList<String> getHours() {
        ArrayList<String> h = new ArrayList<>();
        h.add("1");
        h.add("2");
        h.add("3");
        h.add("4");
        h.add("5");
        h.add("6");
        h.add("7");
        h.add("8");
        h.add("9");
        h.add("10");
        h.add("11");
        h.add("12");
        return h;
    }

}
