package com.fujitsu.deliverycostcalc.entity;

public class WindSpeedRangeRule extends WeatherRangeRule {
    @Override
    public double getWeatherValue(WeatherData data) {
        return data.getWindSpeed();
    }
}
