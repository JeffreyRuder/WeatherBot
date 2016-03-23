package com.example.guest.weatherbot.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.weatherbot.R;
import com.example.guest.weatherbot.adapters.ForecastListAdapter;
import com.example.guest.weatherbot.models.ForecastStatus;
import com.example.guest.weatherbot.models.WeatherStatus;
import com.example.guest.weatherbot.services.ImageFinder;
import com.example.guest.weatherbot.services.OpenWeatherService;
import com.example.guest.weatherbot.services.TemperatureConverter;
import com.example.guest.weatherbot.services.TimeZoneFinderService;
import com.example.guest.weatherbot.services.WindDirectionConverter;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CurrentWeatherActivity extends AppCompatActivity {
    @Bind(R.id.cityNameTextView) TextView mCityNameTextView;
    @Bind(R.id.temperatureTextView) TextView mTemperatureTextView;
    @Bind(R.id.weatherDescriptionTextView) TextView mWeatherDescriptionTextView;
    @Bind(R.id.backgroundImage) ImageView mBackgroundImage;
    @Bind(R.id.sunriseTextView) TextView mSunriseTextView;
    @Bind(R.id.sunsetTextView) TextView mSunsetTextView;
    @Bind(R.id.humidityTextView) TextView mHumidityTextView;
    @Bind(R.id.windTextView) TextView mWindTextView;
    @Bind(R.id.forecastRecyclerView) RecyclerView mRecyclerView;

    public ArrayList<WeatherStatus> mCurrentStatus = new ArrayList<>();
    public ArrayList<ForecastStatus> mForecastArray = new ArrayList<>();

    public String mTimeZoneId;
    private ForecastListAdapter mAdapter;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        getCurrentWeather(location);
        getForecast(location);
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
                mCurrentStatus = openWeatherService.processCurrentWeather(response);

                CurrentWeatherActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Resources res = getResources();
                        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a zzz", Locale.US);

                        try {
                            WeatherStatus currentWeatherStatus = mCurrentStatus.get(0);

                            String windSpeed = String.format(Locale.US, "%.1f", currentWeatherStatus.getWindSpeed() * 2.23694);
                            String windDirection = String.format(Locale.US, WindDirectionConverter.convert(currentWeatherStatus.getWindDegrees()));

                            setTitle(String.format(Locale.US, res.getString(R.string.title_output), currentWeatherStatus.getCityName()));

                            mCityNameTextView.setText(currentWeatherStatus.getCityName());
                            mWeatherDescriptionTextView.setText(WordUtils.capitalize(currentWeatherStatus.getDescription()));
                            mTemperatureTextView.setText(String.format(res.getString(R.string.temperature_output), TemperatureConverter.toFahrenheit(currentWeatherStatus.getTemp())));

                            mHumidityTextView.setText(String.format(res.getString(R.string.humidity_output), currentWeatherStatus.getHumidity()));
                            mWindTextView.setText(String.format(res.getString(R.string.wind_output), windDirection, windSpeed));
                            String image = ImageFinder.findImage(currentWeatherStatus);

                            mSunriseTextView.setText(String.format(res.getString(R.string.sunrise_output), timeFormatter.format(currentWeatherStatus.getSunrise())));
                            mSunsetTextView.setText(String.format(res.getString(R.string.sunset_output), timeFormatter.format(currentWeatherStatus.getSunset())));

                            if (!image.isEmpty()) {
                                Picasso.with(CurrentWeatherActivity.this).load(image).fit().centerCrop().into(mBackgroundImage);
                                mBackgroundImage.setContentDescription(String.format(res.getString(R.string.current_weather_background_content_description), currentWeatherStatus.getDescription()));
                            } else {
                                mBackgroundImage.setBackgroundColor(ContextCompat.getColor(CurrentWeatherActivity.this, R.color.colorPrimaryLight));
                            }
                        } catch (IndexOutOfBoundsException iobe) {
                            iobe.printStackTrace();
                            Toast.makeText(CurrentWeatherActivity.this, "Please wait 5 minutes and try again", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CurrentWeatherActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void getForecast(String location) {
        final OpenWeatherService openWeatherService = new OpenWeatherService(this);
        openWeatherService.getForecastWeather(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mForecastArray = openWeatherService.processForecastWeather(response);

                CurrentWeatherActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new ForecastListAdapter(getApplicationContext(), mForecastArray);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CurrentWeatherActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.addItemDecoration(new VerticalSpaceDecoration(24));
                    }
                });
            }
        });
    }

    private void getTimeZoneId(double lat, double lng, long timestamp) {
        final TimeZoneFinderService timeZoneFinderService = new TimeZoneFinderService(this);
        timeZoneFinderService.getTimeZone(lat, lng, timestamp, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mTimeZoneId = timeZoneFinderService.processResults(response);
            }
        });
    }
}
