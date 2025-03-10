package com.fujitsu.deliverycostcalc;


public class WeatherData {
    // TODO: Use a correct timestamp class and parse it correctly
    private String timestamp;
    private double airTemperature;
    private double windSpeed;
    private Phenomenon phenomenon;

    public WeatherData(String airTemperature, String windSpeed, String phenomenon) {
        this.airTemperature = Double.parseDouble(airTemperature);
        this.windSpeed = Double.parseDouble(windSpeed);
        this.phenomenon = Phenomenon.parsePhenomenon(phenomenon);
    }
}
