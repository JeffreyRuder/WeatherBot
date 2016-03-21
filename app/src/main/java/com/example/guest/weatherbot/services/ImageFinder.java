package com.example.guest.weatherbot.services;

import com.example.guest.weatherbot.R;
import com.example.guest.weatherbot.models.WeatherStatus;

/**
 * Created by Guest on 3/21/16.
 */
public class ImageFinder {

    public static String findImage(WeatherStatus status) {
        String image;
        switch (status.getId()) {
            //thunderstorms
            case 200:case 201:case 202:case 210:case 211:case 212:case 221:case 230:case 231:case 232: image = "http://i.imgur.com/gTuyV33.jpg";
                break;
            //light rain or drizzle
            case 300:case 301:case 302:case 310:case 311:case 312:case 313:case 314:case 321:case 500: image = "http://i.imgur.com/1FJ0zKv.jpg";
                break;
            //medium or heavy rain or showers
            case 501:case 502:case 503:case 520:case 521:case 522:case 531: image = "http://i.imgur.com/ht7Apbj.jpg";
                break;
            //extreme rain
            case 504:
                image = "http://i.imgur.com/fXsz4MD.jpg";
                break;
            //freezing rain or sleet
            case 511:case 611:case 612: image = "http://i.imgur.com/a4m8bIL.jpg";
                break;
            //snow
            case 600:case 601:case 602:case 615:case 616:case 620:case 621:case 622: image = "http://i.imgur.com/pwoPsWT.jpg";
                break;
            //haze or smoke
            case 711:case 721:case 761: image = "http://i.imgur.com/GKYzpvn.jpg";
                break;
            //clear sky
            case 800: image = "http://i.imgur.com/EIJMl2T.jpg";
                break;
            //few clouds
            case 801: image = "http://i.imgur.com/hUMz3ui.jpg";
                break;
            //scattered or broken clouds
            case 802:case 803: image = "http://i.imgur.com/zvamn63.jpg";
                    break;
            //overcast
            case 804: image = "http://i.imgur.com/B7K9F83.jpg";
                break;
            default: image = "";
                break;
        }
        return image;
    }
}
