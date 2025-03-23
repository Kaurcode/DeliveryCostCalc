package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityToVehicleRuleTest {
    @Test
    void appliesTo_shouldReturnTrue_whenCityAndVehicleMatch() throws InvalidMoneyException {
        City tallinn = new City("Tallinn", "Tallinn-Harku");
        Vehicle car = new Vehicle("Car");

        CityToVehicleRule rule = new CityToVehicleRule(
                "Test rule",
                List.of(tallinn),
                List.of(car),
                true,
                new Money("4€")
        );

        PolicyEvaluationInput input = new PolicyEvaluationInput(tallinn, car, null);

        assertTrue(rule.appliesTo(input));
    }

    @Test
    void appliesTo_shouldReturnFalse_whenCityDoesNotMatch() throws InvalidMoneyException {
        City tallinn = new City("Tallinn", "Tallinn-Harku");
        City tartu = new City("Tartu", "Tartu-Tõravere");
        Vehicle car = new Vehicle("Car");

        CityToVehicleRule rule = new CityToVehicleRule(
                "Test rule",
                List.of(tallinn),
                List.of(car),
                true,
                new Money("4€")
        );

        PolicyEvaluationInput input = new PolicyEvaluationInput(tartu, car, null);

        assertFalse(rule.appliesTo(input));
    }

    @Test
    void appliesTo_shouldReturnFalse_whenVehicleDoesNotMatch() throws InvalidMoneyException {
        City tallinn = new City("Tallinn", "Tallinn-Harku");
        Vehicle car = new Vehicle("Car");
        Vehicle bike = new Vehicle("Bike");

        CityToVehicleRule rule = new CityToVehicleRule(
                "Test rule",
                List.of(tallinn),
                List.of(car),
                true,
                new Money("4€")
        );

        PolicyEvaluationInput input = new PolicyEvaluationInput(tallinn, bike, null);

        assertFalse(rule.appliesTo(input));
    }
}
