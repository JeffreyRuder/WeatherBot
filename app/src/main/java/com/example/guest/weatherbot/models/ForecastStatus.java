package com.example.guest.weatherbot.models;

import java.util.Date;

public class ForecastStatus {
    private Date mDate;
    private double mTemp;
    private int mId;
    private String mMain;
    private String mDescription;
    private String mIcon;

    public ForecastStatus (long date, double temp, int id, String main, String description, String icon) {
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
}
