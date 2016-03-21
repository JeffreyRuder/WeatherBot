package com.example.guest.weatherbot.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.guest.weatherbot.R;
import com.example.guest.weatherbot.services.OpenWeatherService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CurrentWeatherActivity extends AppCompatActivity {
    @Bind(R.id.locationTextView) TextView mLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        Resources res = getResources();

        mLocationTextView.setText(String.format(res.getString(R.string.weather_near), location));
        getCurrentWeather(location);
    }

    private void getCurrentWeather(String location) {
        final OpenWeatherService openWeatherService = new OpenWeatherService(this);
        openWeatherService.getCurrentWeather(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        Log.v("JSON DATA", jsonData);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
    }
}
