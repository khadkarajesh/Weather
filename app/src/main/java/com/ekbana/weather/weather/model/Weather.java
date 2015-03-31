package com.ekbana.weather.weather.model;

/**
 * Created by root on 2/24/15.
 */
public class Weather {

    public String locationName;
    public int status;
    public int location_id;

    public String location;
    public String temp;
    public class DaysWeather
    {
        public String day;
        public String low;
        public String high;
        public String condition;
        public String icons;
    }
    public class Current
    {
        public String locationId;
        public String temp;
    }
    public class Forecast
    {
        public String day;
        public String low;
        public String high;
        public String condition;
        public String locationId;
        public String icon;
    }
    public class ForecastHourly
    {
        public String time;
        public String temperature;
        public String conditionTitle;
        public String conditionIcon;
        public String temperatureFeelsLike;
        public String pop;
        public String locationId;
    }

}
