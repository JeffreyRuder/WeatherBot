package com.example.guest.weatherbot.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.guest.weatherbot.R;
import com.example.guest.weatherbot.models.WeatherStatus;
import com.example.guest.weatherbot.ui.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpenWeatherService {
    private Context mContext;

    public OpenWeatherService(Context context) {
        this.mContext = context;
    }

    public void getCurrentWeather(String location, Callback callback) {
        String APPID = mContext.getString(R.string.appid);

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/weather").newBuilder();

        String zipCodePattern = "[0-9]{5}";
        Matcher matcher = Pattern.compile(zipCodePattern).matcher(location);
        if (matcher.find()) {
            String parsedLocation = matcher.group();
            urlBuilder.addQueryParameter("zip", parsedLocation);
        } else {
            String parsedLocation = location.trim();
            urlBuilder.addQueryParameter("q", parsedLocation);
        }
        urlBuilder.addQueryParameter("appid", APPID);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void getForecastWeather(String location, Callback callback) {
        String APPID = mContext.getString(R.string.appid);

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/forecast").newBuilder();
        urlBuilder.addQueryParameter("zip", location);
        urlBuilder.addQueryParameter("appid", APPID);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<WeatherStatus> processCurrentWeather(Response response) {
        ArrayList<WeatherStatus> statuses = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject weatherJSON = new JSONObject(jsonData);
                int id = weatherJSON.getJSONArray("weather").getJSONObject(0).getInt("id");
                String main = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("main");
                String description = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("description");
                String icon = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("icon");
                double temp = weatherJSON.getJSONObject("main").getDouble("temp");
                int pressure = weatherJSON.getJSONObject("main").getInt("pressure");
                int humidity = weatherJSON.getJSONObject("main").getInt("humidity");
                double tempMin = weatherJSON.getJSONObject("main").getDouble("temp_min");
                double tempMax = weatherJSON.getJSONObject("main").getDouble("temp_max");
                double windSpeed = weatherJSON.getJSONObject("wind").getDouble("speed");
                int windDegrees = weatherJSON.getJSONObject("wind").getInt("deg");
                int clouds = weatherJSON.getJSONObject("clouds").getInt("all");
                int dateTime = weatherJSON.getInt("dt");
                long sunrise = weatherJSON.getJSONObject("sys").getInt("sunrise");
                long sunset = weatherJSON.getJSONObject("sys").getInt("sunset");
                int cityId = weatherJSON.getInt("id");
                String cityName = weatherJSON.getString("name");

                WeatherStatus status = new WeatherStatus(id, main, description, icon, temp, pressure, humidity,
                        tempMin, tempMax, windSpeed, windDegrees, clouds, dateTime, sunrise, sunset, cityId, cityName);
                statuses.add(status);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return statuses;
    }

//    public ArrayList<WeatherStatus> processForecastWeather(Response response) {
//        ArrayList<WeatherStatus> statuses = new ArrayList<>();
//        try {
//            String jsonData = response.body().string();
//            if (response.isSuccessful()) {
//                JSONObject weatherJSON = new JSONObject(jsonData);
//                int id = weatherJSON.getJSONArray("weather").getJSONObject(0).getInt("id");
//                String main = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("main");
//                String description = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("description");
//                String icon = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("icon");
//                double temp = weatherJSON.getJSONObject("main").getDouble("temp");
//                int cityId = weatherJSON.getInt("id");
//                String cityName = weatherJSON.getString("name");
//
//                WeatherStatus status = new WeatherStatus(id, main, description, icon, temp, cityId, cityName);
//                statuses.add(status);
//            }
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//        return statuses;
//    }
}
