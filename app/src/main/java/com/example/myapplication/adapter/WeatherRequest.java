package com.example.myapplication.adapter;


import com.example.myapplication.util.Constants;
import com.example.myapplication.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRequest
{
    private static final String TAG = "WeatherRequest";

    byte[] getWeatherByte(URL url) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();

            return outputStream.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getResultAsString(URL url) throws IOException
    {
        return new String(getWeatherByte(url));
    }

    public String getItems(String lat, String lon, String units, String lang) throws IOException
    {
        return getResultAsString(Utils.getWeatherForecastUrl(Constants.WEATHER_ENDPOINT, lat, lon, units, lang));
    }
}
