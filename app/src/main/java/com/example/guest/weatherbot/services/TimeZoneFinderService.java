package com.example.guest.weatherbot.services;

import android.content.Context;
import android.util.Log;

import com.example.guest.weatherbot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TimeZoneFinderService {
    private Context mContext;

    public TimeZoneFinderService(Context context) {
        this.mContext = context;
    }

    public void getTimeZone(double lat, double lng, long timestamp, Callback callback) {
        String key = mContext.getString(R.string.googletime);
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/timezone/json").newBuilder();
        urlBuilder.addQueryParameter("location", lat + "," + lng);
        urlBuilder.addQueryParameter("timestamp", Long.toString(timestamp));
        urlBuilder.addQueryParameter("key", key);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public String processResults (Response response) {
        String results = "";
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject responseJSON = new JSONObject(jsonData);
                results = responseJSON.getString("timeZoneId");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return results;
    }
}
