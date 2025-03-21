package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AIR_TEMPERATURE")
public class AirTemperatureRangeRule extends WeatherRangeRule {
    @Override
    public double getWeatherValue(WeatherData data) {
        return data.getAirTemperature();
    }
}
