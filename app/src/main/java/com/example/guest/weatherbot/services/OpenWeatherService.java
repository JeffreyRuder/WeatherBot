package com.example.guest.weatherbot.services;

import android.app.DownloadManager;
import android.content.Context;
import com.example.guest.weatherbot.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Guest on 3/21/16.
 */
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
}
