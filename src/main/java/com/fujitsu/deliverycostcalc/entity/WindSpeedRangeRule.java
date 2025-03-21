package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("WIND_SPEED")
public class WindSpeedRangeRule extends WeatherRangeRule {
    @Override
    public double getWeatherValue(WeatherData data) {
        return data.getWindSpeed();
    }
}
