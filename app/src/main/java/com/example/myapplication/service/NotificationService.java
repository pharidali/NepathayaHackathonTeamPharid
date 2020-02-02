package com.example.myapplication.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;

import com.example.myapplication.ConnectionDetector;
import com.example.myapplication.R;
import com.example.myapplication.WeatherJSONParser;
import com.example.myapplication.WeatherRequest;
import com.example.myapplication.model.Weather;
import com.example.myapplication.WeatherActivity;
import com.example.myapplication.util.AppPreference;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.Locale;

public class NotificationService extends IntentService {

    private static final String TAG = "NotificationsService";

    public NotificationService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        int NOTIFICATION_ID = (int) (System.currentTimeMillis() % 10000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           		if (Build.VERSION.SDK_INT >= 26) {
			String CHANNEL_ID = "my_app";
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
					"MyApp", NotificationManager.IMPORTANCE_DEFAULT);
			((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_MIN)
                            .setContentTitle(getString(R.string.app_name))
                            .setContentText("Protecting your Phone...").build();
                    startForeground(1, notification);
		}

		else {
                    NotificationCompat.Builder not = new NotificationCompat.Builder(this)
                            .setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_MIN)
                            .setContentTitle("MSecurity")
                            .setContentText("Protecting your Phone...")
                            .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new
                                    Intent(), 0));
                    startForeground(1, not.build());
		}
        }

    }
        @Override
    protected void onHandleIntent(Intent intent) {
        ConnectionDetector checkNetwork = new ConnectionDetector(this);
        if (!checkNetwork.isNetworkAvailableAndConnected()) {
            return;
        }

        SharedPreferences preferences = getSharedPreferences(Constants.APP_SETTINGS_NAME, 0);
        String latitude = preferences.getString(Constants.APP_SETTINGS_LATITUDE, "51.51");
        String longitude = preferences.getString(Constants.APP_SETTINGS_LONGITUDE, "-0.13");
        String units = AppPreference.getTemperatureUnit(this);

        Weather weather;
        try {
            String weatherRaw = new WeatherRequest().getItems(latitude, longitude, units, "en");
            weather = WeatherJSONParser.getWeather(weatherRaw);

            AppPreference.saveLastUpdateTimeMillis(this);
            AppPreference.saveWeather(this, weather);
            weatherNotification(weather);
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error get weather", e);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NotificationService.class);
    }

    public static void setNotificationServiceAlarm(Context context,
                                                   boolean isNotificationEnable) {
        Intent intent = NotificationService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,
                                                               PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        String intervalPref = AppPreference.getInterval(context);
        long intervalMillis = Utils.intervalMillisForAlarm(intervalPref);
        if (isNotificationEnable) {

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                             SystemClock.elapsedRealtime() + intervalMillis,
                                             intervalMillis,
                                             pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    private void weatherNotification(Weather weather) {
        Intent intent = new Intent(this, WeatherActivity.class);
        PendingIntent launchIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String temperatureScale = Utils.getTemperatureScale(this);
        String speedScale = Utils.getSpeedScale(this);

        String temperature = String.format(Locale.getDefault(), "%.1f",
                                           weather.temperature.getTemp());

        String wind = getString(R.string.wind_label, String.format(Locale.getDefault(),
                                                                   "%.1f",
                                                                   weather.wind.getSpeed()),
                                speedScale);
        String humidity = getString(R.string.humidity_label,
                                    String.valueOf(weather.currentCondition.getHumidity()),
                                    getString(R.string.percent_sign));
        String pressure = getString(R.string.pressure_label,
                                    String.format(Locale.getDefault(), "%.1f",
                                                  weather.currentCondition.getPressure()),
                                    getString(R.string.pressure_measurement));
        String cloudiness = getString(R.string.pressure_label,
                                      String.valueOf(weather.cloud.getClouds()),
                                      getString(R.string.percent_sign));

        StringBuilder notificationText = new StringBuilder(wind)
                .append("  ")
                .append(humidity)
                .append("  ")
                .append(pressure)
                .append("  ")
                .append(cloudiness);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentIntent(launchIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(temperature
                        + temperatureScale
                        + "  "
                        + weather.location.getCityName()
                        + ", "
                        + weather.location.getCountryCode())
                .setContentTitle(temperature
                        + temperatureScale
                        + "  "
                        + weather.currentWeather.getDescription())
                .setContentText(notificationText)
                .setVibrate(isVibrateEnabled())
                .setAutoCancel(true);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setOnlyAlertOnce(true);
        notificationManager.notify(1, mBuilder.build());

        //notificationManager.notify(notificationId,notification.build());

    }

    private long[] isVibrateEnabled() {
        if (!AppPreference.isVibrateEnabled(this)) {
            return null;
        }
        return new long[]{500, 500, 500};
    }
}
