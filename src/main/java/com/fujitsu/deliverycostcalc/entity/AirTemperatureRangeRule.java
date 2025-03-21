package com.fujitsu.deliverycostcalc.entity;

public class AirTemperatureRangeRule extends WeatherRangeRule {
    @Override
    public double getWeatherValue(WeatherData data) {
        return data.getAirTemperature();
    }
}
