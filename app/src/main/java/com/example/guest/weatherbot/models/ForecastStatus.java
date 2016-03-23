package com.example.guest.weatherbot.models;

import java.util.Date;

public class ForecastStatus {
    private double mLat;
    private double mLng;
    private Date mDate;
    private double mTemp;
    private int mId;
    private String mMain;
    private String mDescription;
    private String mIcon;

    public ForecastStatus (double lat, double lng, long date, double temp, int id, String main, String description, String icon) {
        this.mLat = lat;
        this.mLng = lng;
        this.mDate = new Date(date * 1000);
        this.mTemp = temp;
        this.mId = id;
        this.mMain = main;
        this.mDescription = description;
        this.mIcon = icon;
    }

    public Date getDate() {
        return mDate;
    }

    public double getTemp() {
        return mTemp;
    }

    public int getId() {
        return mId;
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

    public double getLat() {
        return mLat;
    }

    public double getLng() {
        return mLng;
    }
}
