package com.example.guest.weatherbot.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.guest.weatherbot.R;
import com.example.guest.weatherbot.models.WeatherStatus;
import com.example.guest.weatherbot.services.OpenWeatherService;
import com.example.guest.weatherbot.services.TemperatureConverter;

import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CurrentWeatherActivity extends AppCompatActivity {
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.cityNameTextView) TextView mCityNameTextView;
    @Bind(R.id.temperatureTextView) TextView mTemperatureTextView;
    @Bind(R.id.weatherDescriptionTextView) TextView mWeatherDescriptionTextView;

    public ArrayList<WeatherStatus> mStatuses = new ArrayList<>();
    private final String TAG = this.getClass().getSimpleName();

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
                mStatuses = openWeatherService.processCurrentWeather(response);

                CurrentWeatherActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Resources res = getResources();
                        WeatherStatus status = mStatuses.get(0);
                        mCityNameTextView.setText(status.getCityName());
                        mWeatherDescriptionTextView.setText(WordUtils.capitalize(status.getDescription()));
                        mTemperatureTextView.setText(String.format(res.getString(R.string.temperature_output), TemperatureConverter.toFahrenheit(status.getTemp())));
                    }
                });
            }
        });
    }
}
