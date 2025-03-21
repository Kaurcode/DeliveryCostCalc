package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("WIND_SPEED")
public class WindSpeedRangeRule extends WeatherRangeRule {
    protected WindSpeedRangeRule() {}

    public WindSpeedRangeRule(
            String description,
            boolean hasStartValue,
            boolean isStartInclusive,
            double startValue,
            boolean hasEndValue,
            boolean isEndInclusive,
            double endValue,
            List<Vehicle> vehicles,
            boolean isAllowed,
            Money money
    ) {
        super(
                description,
                hasStartValue,
                isStartInclusive,
                startValue,
                hasEndValue,
                isEndInclusive,
                endValue,
                vehicles,
                isAllowed,
                money
        );
    }

    @Override
    public double getWeatherValue(WeatherData data) {
        return data.getWindSpeed();
    }
}
