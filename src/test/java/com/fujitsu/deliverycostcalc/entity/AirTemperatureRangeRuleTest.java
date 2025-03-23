package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AirTemperatureRangeRuleTest {

    private final Vehicle bike = new Vehicle("Bike");

    private WeatherData makeWeather(double airTemp) {
        WeatherData mock = new WeatherData();
        mock.setAirTemperature(airTemp);
        return mock;
    }

    @Test
    void appliesTo_shouldReturnTrue_whenInRangeAndVehicleAllowed() throws InvalidMoneyException {
        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp -10 to 10 inclusive",
                true, true, -10.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        PolicyEvaluationInput input = new PolicyEvaluationInput(null, bike, makeWeather(0.0));
        assertTrue(rule.appliesTo(input));
    }

    @Test
    void appliesTo_shouldReturnFalse_whenVehicleNotIncluded() throws InvalidMoneyException {
        Vehicle scooter = new Vehicle("Scooter");

        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp -10 to 10",
                true, true, -10.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        PolicyEvaluationInput input = new PolicyEvaluationInput(null, scooter, makeWeather(0.0));
        assertFalse(rule.appliesTo(input));
    }

    @Test
    void appliesTo_shouldRespectInclusiveStartAndEnd() throws InvalidMoneyException {
        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp -10 to 10 inclusive",
                true, true, -10.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(-10.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.0))));
    }

    @Test
    void appliesTo_shouldRejectExclusiveStartAndEndBoundaries() throws InvalidMoneyException {
        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp > -10 and < 10",
                true, false, -10.0,
                true, false, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(-10.0))));
        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(0.0))));
    }

    @Test
    void appliesTo_shouldReturnTrue_whenOnlyStartValueApplies() throws InvalidMoneyException {
        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp ≥ -5",
                true, true, -5.0,
                false, false, 0.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(-5.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(5.0))));
        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(-5.1))));
    }

    @Test
    void appliesTo_shouldReturnTrue_whenOnlyEndValueApplies() throws InvalidMoneyException {
        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp ≤ 10",
                false, false, 0.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(-50.0))));
        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.1))));
    }

    @Test
    void appliesTo_shouldRespectExclusivity_onSingleBoundStart() throws InvalidMoneyException {
        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp > 0",
                true, false, 0.0,
                false, false, 0.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(0.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(0.1))));
    }

    @Test
    void appliesTo_shouldRespectExclusivity_onSingleBoundEnd() throws InvalidMoneyException {
        AirTemperatureRangeRule rule = new AirTemperatureRangeRule(
                "Temp < 10",
                false, false, 0.0,
                true, false, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(9.9))));
    }
}
