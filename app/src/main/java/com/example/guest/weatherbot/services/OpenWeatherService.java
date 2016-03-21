package com.example.guest.weatherbot.services;

import android.content.Context;
import com.example.guest.weatherbot.R;
import com.example.guest.weatherbot.models.WeatherStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
                int cityId = weatherJSON.getInt("id");
                String cityName = weatherJSON.getString("name");

                WeatherStatus status = new WeatherStatus(id, main, description, icon, temp, cityId, cityName);
                statuses.add(status);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return statuses;
    }
}
