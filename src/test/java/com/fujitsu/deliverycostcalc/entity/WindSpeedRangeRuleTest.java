package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WindSpeedRangeRuleTest {

    private final Vehicle bike = new Vehicle("Bike");

    private WeatherData makeWeather(double windSpeed) {
        WeatherData mock = new WeatherData();
        mock.setWindSpeed(windSpeed);
        return mock;
    }

    @Test
    void appliesTo_shouldReturnTrue_whenInRangeAndVehicleAllowed() throws InvalidMoneyException {
        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind 5–10 m/s, inclusive",
                true, true, 5.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        PolicyEvaluationInput input = new PolicyEvaluationInput(
                null, bike, makeWeather(7.0)
        );

        assertTrue(rule.appliesTo(input));
    }

    @Test
    void appliesTo_shouldReturnFalse_whenVehicleNotIncluded() throws InvalidMoneyException {
        Vehicle scooter = new Vehicle("Scooter");

        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind 5–10 m/s",
                true, true, 5.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        PolicyEvaluationInput input = new PolicyEvaluationInput(
                null, scooter, makeWeather(7.0)
        );

        assertFalse(rule.appliesTo(input));
    }

    @Test
    void appliesTo_shouldRespectInclusiveStartAndEnd() throws InvalidMoneyException {
        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind 5–10 m/s inclusive",
                true, true, 5.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(5.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.0))));
    }

    @Test
    void appliesTo_shouldRejectExclusiveStartAndEndBoundaries() throws InvalidMoneyException {
        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind >5 and <10 m/s",
                true, false, 5.0,
                true, false, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(5.0))));
        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(7.5))));
    }


    @Test
    void appliesTo_shouldReturnTrue_whenOnlyStartValueApplies() throws InvalidMoneyException {
        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind ≥ 5 m/s",
                true, true, 5.0,
                false, false, 0.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(5.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(15.0))));
        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(4.9))));
    }

    @Test
    void appliesTo_shouldReturnTrue_whenOnlyEndValueApplies() throws InvalidMoneyException {
        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind ≤ 10 m/s",
                false, false, 0.0,
                true, true, 10.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(0.0))));
        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(10.1))));
    }

    @Test
    void appliesTo_shouldRespectExclusivity_onSingleBoundStart() throws InvalidMoneyException {
        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind > 5 m/s",
                true, false, 5.0,
                false, false, 0.0,
                List.of(bike),
                true,
                new Money("1€")
        );

        assertFalse(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(5.0))));
        assertTrue(rule.appliesTo(new PolicyEvaluationInput(null, bike, makeWeather(5.1))));
    }

    @Test
    void appliesTo_shouldRespectExclusivity_onSingleBoundEnd() throws InvalidMoneyException {
        WindSpeedRangeRule rule = new WindSpeedRangeRule(
                "Wind < 10 m/s",
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