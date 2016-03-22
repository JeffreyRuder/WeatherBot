package com.example.guest.weatherbot.services;

public class TemperatureConverter {
    public static String toFahrenheit(double kelvin) {
        Double fahrenheit = (kelvin * 1.8) - 459.67;
        return String.format("%.1f", fahrenheit);
    }
}
