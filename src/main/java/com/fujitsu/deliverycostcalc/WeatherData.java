package com.fujitsu.deliverycostcalc;


import java.time.Instant;

public class WeatherData {
    private Instant timestamp;
    private double airTemperature;
    private double windSpeed;
    private Phenomenon phenomenon;

    public WeatherData(String timestamp, String airTemperature, String windSpeed, String phenomenon) {
        this.timestamp = Instant.ofEpochSecond(Long.parseLong(timestamp));
        this.airTemperature = Double.parseDouble(airTemperature);
        this.windSpeed = Double.parseDouble(windSpeed);
        this.phenomenon = Phenomenon.parsePhenomenon(phenomenon);
    }
}
