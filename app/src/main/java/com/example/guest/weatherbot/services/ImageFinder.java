package com.example.guest.weatherbot.services;

import com.example.guest.weatherbot.models.WeatherStatus;

public class ImageFinder {

    public static String findImage(WeatherStatus status) {
        String image = "";
        String icon = status.getIcon();
        boolean isDaytime = icon.contains("d");

        if (isDaytime) {
            if (icon.contains("11")) {
                //thunderstorm
                image = "http://i.imgur.com/IgBdFdq.jpg";
            } else if (icon.contains("09")) {
                //light rain
                image = "http://i.imgur.com/ZR3SPdB.jpg";
            } else if (icon.contains("10")) {
                //rain
                image = "http://i.imgur.com/fXsz4MD.jpg";
            } else if (icon.contains("13")) {
                //snow
                image = "http://i.imgur.com/pwoPsWT.jpg";
            } else if (icon.contains("50")) {
                //mist, smoke, haze
                image = "http://i.imgur.com/GKYzpvn.jpg";
            } else if (icon.contains("01")) {
                //clear
                image = "http://i.imgur.com/BRLYPfA.jpg";
            } else if (icon.contains("02")) {
                //light clouds
                image = "http://i.imgur.com/lHepOUn.jpg";
            } else if (icon.contains("03")) {
                //medium clouds
                image = "http://i.imgur.com/hUMz3ui.jpg";
            } else if (icon.contains("04")) {
                //heavy clouds
                image = "http://i.imgur.com/B7K9F83.jpg";
            }
        }

        if (!isDaytime) {
            if (icon.contains("11")) {
                //thunderstorm
                image = "http://i.imgur.com/yGbVFzJ.jpg";
            } else if (icon.contains("09")) {
                //light rain
                image = "http://i.imgur.com/ht7Apbj.jpg";
            } else if (icon.contains("10")) {
                //rain
                image = "http://i.imgur.com/ht7Apbj.jpg";
            } else if (icon.contains("13")) {
                //snow
                image = "http://i.imgur.com/xignJlY.jpg";
            } else if (icon.contains("50")) {
                //mist, smoke, haze
                image = "http://i.imgur.com/yk9mNHW.jpg";
            } else if (icon.contains("01")) {
                //clear
                image = "http://i.imgur.com/ys0jbgs.jpg";
            } else if (icon.contains("02")) {
                //light clouds
                image = "http://i.imgur.com/GIe9imk.jpg";
            } else if (icon.contains("03")) {
                //medium clouds
                image = "http://i.imgur.com/xCVgJr7.jpg";
            } else if (icon.contains("04")) {
                //heavy clouds
                image = "http://i.imgur.com/xCVgJr7.jpg";
            }
        }

        return image;
    }
}
