package com.example.guest.weatherbot.models;

import com.example.guest.weatherbot.services.TimeZoneFinderService;

import java.util.Date;

public class WeatherStatus {
    private int mId;
    private double mLat;
    private double mLng;
    private String mMain;
    private String mDescription;
    private String mIcon;
    private double mTemp;
    private int mPressure;
    private int mHumidity;
    private double mMin;
    private double mMax;
    private double mWindSpeed;
    private int mWindDegrees;
    private int mClouds;
    private Date dateTime;
    private long timestamp;
    private Date mSunrise;
    private Date mSunset;
    private int mCityId;
    private String mCityName;

    public WeatherStatus(int mId, double mLat, double mLng, String mMain, String mDescription, String mIcon, double mTemp,
                         int mPressure, int mHumidity, double mMin, double mMax, double mWindSpeed,
                         int mWindDegrees, int mClouds, long dateTime, long mSunrise, long mSunset, int mCityId, String mCityName) {
        this.mId = mId;
        this.mLat = mLat;
        this.mLng = mLng;
        this.mMain = mMain;
        this.mDescription = mDescription;
        this.mIcon = mIcon;
        this.mTemp = mTemp;
        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
        this.mMin = mMin;
        this.mMax = mMax;
        this.mWindSpeed = mWindSpeed;
        this.mWindDegrees = mWindDegrees;
        this.mClouds = mClouds;
        this.dateTime = new Date(dateTime * 1000);
        this.timestamp = dateTime;
        this.mSunrise = new Date(mSunrise * 1000);
        this.mSunset = new Date(mSunset * 1000);
        this.mCityId = mCityId;
        this.mCityName = mCityName;
    }

    public int getId() {
        return mId;
    }

    public double getLat() {
        return mLat;
    }

    public double getLng() {
        return mLng;
    }

    public String getMain() {
        return mMain;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getIcon() {
        return mIcon;
    }

    public double getTemp() {
        return mTemp;
    }

    public int getPressure() {
        return mPressure;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public double getMin() {
        return mMin;
    }

    public double getMax() {
        return mMax;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public int getWindDegrees() {
        return mWindDegrees;
    }

    public int getClouds() {
        return mClouds;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public Date getSunrise() {
        return mSunrise;
    }

    public Date getSunset() {
        return mSunset;
    }

    public int getCityId() {
        return mCityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
