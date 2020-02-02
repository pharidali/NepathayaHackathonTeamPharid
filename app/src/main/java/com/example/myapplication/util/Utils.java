package com.example.myapplication.util;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.format.DateFormat;

import com.example.myapplication.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Pattern;

import androidx.preference.PreferenceManager;
import android.util.Log;

import com.example.myapplication.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Utils {
    public static AlertDialog mAlertDialog;
    public static final String OPEN_WEATHER_MAP_API_KEY = "1487dd8a93bfd85d278d9ac8dcfee94c";

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );



    public static String getStrIcon(Context context, String iconId) {
        String icon;
        switch (iconId) {
            case "01d":
                icon = context.getString(R.string.icon_clear_sky_day);
                break;
            case "01n":
                icon = context.getString(R.string.icon_clear_sky_night);
                break;
            case "02d":
                icon = context.getString(R.string.icon_few_clouds_day);
                break;
            case "02n":
                icon = context.getString(R.string.icon_few_clouds_night);
                break;
            case "03d":
                icon = context.getString(R.string.icon_scattered_clouds);
                break;
            case "03n":
                icon = context.getString(R.string.icon_scattered_clouds);
                break;
            case "04d":
                icon = context.getString(R.string.icon_broken_clouds);
                break;
            case "04n":
                icon = context.getString(R.string.icon_broken_clouds);
                break;
            case "09d":
                icon = context.getString(R.string.icon_shower_rain);
                break;
            case "09n":
                icon = context.getString(R.string.icon_shower_rain);
                break;
            case "10d":
                icon = context.getString(R.string.icon_rain_day);
                break;
            case "10n":
                icon = context.getString(R.string.icon_rain_night);
                break;
            case "11d":
                icon = context.getString(R.string.icon_thunderstorm);
                break;
            case "11n":
                icon = context.getString(R.string.icon_thunderstorm);
                break;
            case "13d":
                icon = context.getString(R.string.icon_snow);
                break;
            case "13n":
                icon = context.getString(R.string.icon_snow);
                break;
            case "50d":
                icon = context.getString(R.string.icon_mist);
                break;
            case "50n":
                icon = context.getString(R.string.icon_mist);
                break;
            default:
                icon = context.getString(R.string.icon_weather_default);
        }

        return icon;
    }

    public static String getTemperatureScale(Context context) {
        String[] temperatureScaleArray = context.getResources().getStringArray(
                R.array.pref_temperature_entries);
        String unitPref = AppPreference.getTemperatureUnit(context);
        return unitPref.equals("metric") ?
                temperatureScaleArray[0] : temperatureScaleArray[1];
    }

    public static String getSpeedScale(Context context) {
        String unitPref = AppPreference.getTemperatureUnit(context);
        return unitPref.equals("metric") ?
                context.getString(R.string.wind_speed_meters) :
                context.getString(R.string.wind_speed_miles);
    }

    public static String setLastUpdateTime(Context context, long lastUpdate) {
        Date lastUpdateTime = new Date(lastUpdate);
        return DateFormat.getTimeFormat(context).format(lastUpdateTime);
    }

    public static long intervalMillisForAlarm(String intervalMinutes) {
        int interval = Integer.parseInt(intervalMinutes);
        switch (interval) {
            case 15:
                return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
            case 30:
                return AlarmManager.INTERVAL_HALF_HOUR;
            case 60:
                return AlarmManager.INTERVAL_HOUR;
            case 720:
                return AlarmManager.INTERVAL_HALF_DAY;
            case 1440:
                return AlarmManager.INTERVAL_DAY;
            default:
                return interval * 60 * 1000;
        }
    }

    public static String unixTimeToFormatTime(Context context, long unixTime) {
        long unixTimeToMillis = unixTime * 1000;
        return DateFormat.getTimeFormat(context).format(unixTimeToMillis);
    }


    public static URL getWeatherForecastUrl(String endpoint, String lat, String lon, String units, String lang) throws
            MalformedURLException {
        String url = Uri.parse(endpoint)
                .buildUpon()
                .appendQueryParameter("appid", OPEN_WEATHER_MAP_API_KEY)
                .appendQueryParameter("lat", lat)
                .appendQueryParameter("lon", lon)
                .appendQueryParameter("units", units)
                .appendQueryParameter("lang", "cs".equalsIgnoreCase(lang)?"cz":lang)
                .build()
                .toString();
        return new URL(url);
    }

    public static String getCityAndCountry(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SETTINGS_NAME, 0);

        if(AppPreference.isGeocoderEnabled(context)) {
            return getCityAndCountryFromGeolocation(preferences);
        } else {
            return getCityAndCountryFromPreference(context);
        }
    }

    private static String getCityAndCountryFromGeolocation(SharedPreferences preferences) {
        String geoCountryName = preferences.getString(Constants.APP_SETTINGS_GEO_COUNTRY_NAME, "United Kingdom");
        String geoCity = preferences.getString(Constants.APP_SETTINGS_GEO_CITY, "London");
        if("".equals(geoCity)) {
            return geoCountryName;
        }
        String geoDistrictOfCity = preferences.getString(Constants.APP_SETTINGS_GEO_DISTRICT_OF_CITY, "");
        if ("".equals(geoDistrictOfCity) || geoCity.equalsIgnoreCase(geoDistrictOfCity)) {
            return geoCity + ", " + geoCountryName;
        }
        return geoCity + " - " + geoDistrictOfCity + ", " + geoCountryName;
    }

    private static String getCityAndCountryFromPreference(Context context) {
        String[] cityAndCountryArray = AppPreference.getCityAndCode(context);
        return cityAndCountryArray[0] + ", " + cityAndCountryArray[1];
    }








    private static int loadIcons = -1;


    public static String getCacheFile(Context ctx, String theURI) {
        StringBuilder chaine = new StringBuilder("");
        try{
            String aFileName = theURI.toLowerCase().replace("http://","");
            aFileName = aFileName.toLowerCase().replace("https://","");
            aFileName = sanitizeName(aFileName);

            File file = new File(ctx.getCacheDir().getAbsolutePath() + "/"+aFileName);
            Date lastModDate = new Date(file.lastModified());

            Date now = new Date();
            long millis = now.getTime() - file.lastModified();
            long secs = millis / 1000;
            long mins = secs/60;
            long hours = mins/60;

            if(BuildConfig.DEBUG) { Log.d("UTIL","File last modified : "+ lastModDate.toString() + " secs="+secs+"  mins="+mins+" hours="+hours); }

            if (hours < 1) {
                FileInputStream aStream = new FileInputStream(file);
                BufferedReader rd = new BufferedReader(new InputStreamReader(aStream));
                String line;
                while ((line = rd.readLine()) != null) {
                    chaine.append(line);
                }
                rd.close();
                if(BuildConfig.DEBUG) { Log.d("UTIL", "used cache for:" + theURI); }
                return chaine.toString();
            }
            if(BuildConfig.DEBUG) { Log.d("UTIL", "do not use cache, because too old:" + theURI); }
            return null;
        }
        catch(Exception e){
            Log.e("UTIL","getCacheFile() "+e);
        }
        return null;
    }

    public static void writeFileCache(Context ctx, String theURI, String content){
        try{
            String aFileName = theURI.toLowerCase().replace("http://","");
            aFileName = aFileName.toLowerCase().replace("https://","");
            aFileName = sanitizeName(aFileName);

            File f = new File(ctx.getCacheDir() + "/" + aFileName);
            FileOutputStream aStream = new FileOutputStream(f);
            aStream.write(content.getBytes("utf-8"));
            aStream.close();
        }
        catch(Exception e){
            Log.e("UTIL","writeFileCache() could not write to cache file for:"+theURI);
        }
    }


    public static boolean shouldLoadIcons(final Context context) {
        switch(loadIcons) {
            case -1:
                if(PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean("load_icons", false)) {
                    loadIcons = 1;
                    return true;
                } else {
                    loadIcons = 0;
                    return true;
                }
            case 0:
                return false;
            case 1:
                return true;
        }
        return false;
    }

    public static String sanitizeName(String str) {
        return str.replaceAll("\\W+", "_").replaceAll("^_+", "").replaceAll("_+$", "");
    }

}
