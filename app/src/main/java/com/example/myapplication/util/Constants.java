package com.example.myapplication.util;

public class Constants {

    /**
     * SharedPreference names
     */
    public static final String APP_SETTINGS_NAME = "config";
    public static final String PREF_WEATHER_NAME = "weather_pref";

    /**
     * Preferences constants
     */
    public static final String APP_SETTINGS_LATITUDE = "latitude";
    public static final String APP_SETTINGS_LONGITUDE = "longitude";
    public static final String APP_SETTINGS_CITY = "city";
    public static final String APP_SETTINGS_COUNTRY_CODE = "country_code";
    public static final String APP_SETTINGS_GEO_COUNTRY_NAME = "geo_country_name";
    public static final String APP_SETTINGS_GEO_DISTRICT_OF_CITY = "geo_district_name";
    public static final String APP_SETTINGS_GEO_CITY = "geo_city_name";
    public static final String LAST_UPDATE_TIME_IN_MS = "last_update";
    
    public static final String KEY_PREF_IS_NOTIFICATION_ENABLED = "notification_pref_key";
    public static final String KEY_PREF_TEMPERATURE = "temperature_pref_key";
    public static final String KEY_PREF_HIDE_DESCRIPTION = "hide_desc_pref_key";
    public static final String KEY_PREF_INTERVAL_NOTIFICATION = "notification_interval_pref_key";
    public static final String KEY_PREF_VIBRATE = "notification_vibrate_pref_key";
    public static final String KEY_PREF_WIDGET_USE_GEOCODER = "widget_use_geocoder_pref_key";
    public static final String KEY_PREF_WIDGET_THEME = "widget_theme_pref_key";



    public static final String WEATHER_DATA_TEMPERATURE = "temperature";
    public static final String WEATHER_DATA_DESCRIPTION = "description";
    public static final String WEATHER_DATA_PRESSURE = "pressure";
    public static final String WEATHER_DATA_HUMIDITY = "humidity";
    public static final String WEATHER_DATA_WIND_SPEED = "wind_speed";
    public static final String WEATHER_DATA_CLOUDS = "clouds";
    public static final String WEATHER_DATA_ICON = "icon";
    public static final String WEATHER_DATA_SUNRISE = "sunrise";
    public static final String WEATHER_DATA_SUNSET = "sunset";

    /**
     * Widget action constants
     */
    public static final String ACTION_FORCED_APPWIDGET_UPDATE =
            "org.asdtm.goodweather.action.FORCED_APPWIDGET_UPDATE";


    public static final String WEATHER_ENDPOINT = "http://api.openweathermap.org/data/2.5/weather";

}
