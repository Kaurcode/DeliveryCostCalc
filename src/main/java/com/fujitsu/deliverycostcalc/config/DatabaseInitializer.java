package com.fujitsu.deliverycostcalc.config;

import com.fujitsu.deliverycostcalc.entity.*;
import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import com.fujitsu.deliverycostcalc.service.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Ensures default database configuration according to the project rules
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {
    private static final Map<String, String> DEFAULT_CITIES_TO_STATION_NAMES = Map.of(
            "Tallinn", "Tallinn-Harku",
            "Tartu", "Tartu-Tõravere",
            "Pärnu", "Pärnu"
    );

    private static final Set<String> DEFAULT_VEHICLES = Set.of("Car", "Scooter", "Bike");

    private final CityService cityService;
    private final FeePolicyService feePolicyService;
    private final VehicleService vehicleService;

    private final WeatherRangeRuleService weatherRangeRuleService;
    private final WeatherPhenomenonRuleService weatherPhenomenonRuleService;
    private final CityToVehicleRuleService cityToVehicleRuleService;

    public DatabaseInitializer(
            CityService cityService, FeePolicyService feePolicyService, VehicleService vehicleService,
            WeatherRangeRuleService weatherRangeRuleService, WeatherPhenomenonRuleService weatherPhenomenonRuleService,
            CityToVehicleRuleService cityToVehicleRuleService
    ) {
        this.cityService = cityService;
        this.feePolicyService = feePolicyService;
        this.vehicleService = vehicleService;

        this.weatherRangeRuleService = weatherRangeRuleService;
        this.weatherPhenomenonRuleService = weatherPhenomenonRuleService;
        this.cityToVehicleRuleService = cityToVehicleRuleService;
    }

    @Override
    @Transactional
    public void run(String... args) throws InvalidMoneyException {
        ensureDefaultConfiguration();
    }

    protected void ensureDefaultConfiguration() throws InvalidMoneyException {
        ensureDefaultCitiesExist();
        ensureDefaultVehiclesExist();

        if (feePolicyService.isEmpty()) {
            insertDefaultRules();
        }
    }

    private void ensureDefaultCitiesExist() {
        HashSet<String> stationNames = cityService.getStationNamesAsSet();

        for (Map.Entry<String, String> entry : DEFAULT_CITIES_TO_STATION_NAMES.entrySet()) {
            String cityName = entry.getKey();
            String stationName = entry.getValue();

            if (!stationNames.contains(stationName)) {
                City city = new City(cityName, stationName);
                cityService.save(city);
            }
        }
    }

    private void ensureDefaultVehiclesExist() {
        HashSet<String> vehicleNames = vehicleService.getVehicleTypesAsSet();

        for (String vehicleType : DEFAULT_VEHICLES) {
            if (!vehicleNames.contains(vehicleType)) {
                Vehicle vehicle = new Vehicle(vehicleType);
                vehicleService.save(vehicle);
            }
        }
    }

    private void insertDefaultRules() throws InvalidMoneyException {
        insertCarToVehicleRules();
        insertWeatherRules();
    }

    private void insertWeatherRules() throws InvalidMoneyException {
        Vehicle Scooter = vehicleService.findByType("Scooter").orElseThrow();
        Vehicle Bike = vehicleService.findByType("Bike").orElseThrow();

        AirTemperatureRangeRule lessThanMinusTenATEF = new AirTemperatureRangeRule(
                "Extra fee based on air temperature (ATEF): " +
                        "If vehicle type is scooter or bike and air temperature is less than -10°C, " +
                        "then ATEF = 1€",
                false,
                false,
                0,
                true,
                false,
                -10.0,
                List.of(Scooter, Bike),
                true,
                new Money("1€")
        );

        AirTemperatureRangeRule betweenMinusTenAndZeroATEF = new AirTemperatureRangeRule(
                "Extra fee based on air temperature (ATEF): " +
                        "If vehicle type is scooter or bike and air temperature is between -10°C and 0°C, " +
                        "then ATEF = 0,5€",
                true,
                true,
                -10.0,
                true,
                true,
                0.0,
                List.of(Scooter, Bike),
                true,
                new Money("0,5€")
        );

        WindSpeedRangeRule tenToTwentyWSEF = new WindSpeedRangeRule(
                "Extra fee based on wind speed (WSEF): " +
                        "If vehicle type is bike and wind speed is between 10 m/s and 20 m/s, then WSEF = 0,5€",
                true,
                true,
                10.0,
                true,
                true,
                20.0,
                List.of(Bike),
                true,
                new Money("0,5€")
        );

        WindSpeedRangeRule overTwentyWSEF = new WindSpeedRangeRule(
                "Extra fee based on wind speed (WSEF): " +
                        "If vehicle type is bike and wind speed is over 20 m/s, " +
                        "then usage of selected vehicle type is forbidden",
                true,
                false,
                20.0,
                false,
                false,
                0,
                List.of(Bike),
                false,
                null
        );

        WeatherPhenomenonRule snowOrSleetWPEF = new WeatherPhenomenonRule(
                "Extra fee based on weather phenomenon (WPEF): " +
                        "If vehicle type is scooter or bike and weather phenomenon is related to snow or sleet, " +
                        "then WPEF = 1€",
                List.of(Phenomenon.SNOWY),
                List.of(Scooter, Bike),
                true,
                new Money("1€")
        );

        WeatherPhenomenonRule rainWPEF = new WeatherPhenomenonRule(
                "Extra fee based on weather phenomenon (WPEF): " +
                        "If vehicle type is scooter or bike and weather phenomenon is related to rain, " +
                        "then WPEF = 0,5€",
                List.of(Phenomenon.RAINY),
                List.of(Scooter, Bike),
                true,
                new Money("0,5€")
        );

        WeatherPhenomenonRule stormyWPEF = new WeatherPhenomenonRule(
                "Extra fee based on weather phenomenon (WPEF): " +
                        "If vehicle type is scooter or bike and " +
                        "weather phenomenon is related to glaze, hail, or thunder, " +
                        "then usage of selected vehicle type is forbidden",
                List.of(Phenomenon.STORMY),
                List.of(Scooter, Bike),
                false,
                null
        );

        weatherRangeRuleService.saveAll(List.of(
                lessThanMinusTenATEF, betweenMinusTenAndZeroATEF, tenToTwentyWSEF, overTwentyWSEF
        ));
        weatherPhenomenonRuleService.saveAll(List.of(snowOrSleetWPEF, rainWPEF, stormyWPEF));
    }

    private void insertCarToVehicleRules() throws InvalidMoneyException {
        City Tallinn = cityService.findByName("Tallinn").orElseThrow();
        City Tartu = cityService.findByName("Tartu").orElseThrow();
        City Parnu = cityService.findByName("Pärnu").orElseThrow();

        Vehicle Car = vehicleService.findByType("Car").orElseThrow();
        Vehicle Scooter = vehicleService.findByType("Scooter").orElseThrow();
        Vehicle Bike = vehicleService.findByType("Bike").orElseThrow();

        CityToVehicleRule tallinnCarFee = new CityToVehicleRule(
                "Regional base car fee in Tallinn: 4€",
                List.of(Tallinn),
                List.of(Car),
                true,
                new Money("4€")
        );

        CityToVehicleRule tallinnScooterFee = new CityToVehicleRule(
                "Regional base scooter fee in Tallinn: 3.5€",
                List.of(Tallinn),
                List.of(Scooter),
                true,
                new Money("3.5€")
        );

        CityToVehicleRule tallinnBikeFee = new CityToVehicleRule(
                "Regional base bike fee in Tallinn: 3€",
                List.of(Tallinn),
                List.of(Bike),
                true,
                new Money("3€")
        );

        CityToVehicleRule tartuCarFee = new CityToVehicleRule(
                "Regional base car fee in Tartu: 3.5€",
                List.of(Tartu),
                List.of(Car),
                true,
                new Money("3.5€")
        );

        CityToVehicleRule tartuScooterFee = new CityToVehicleRule(
                "Regional base scooter fee in Tartu: 3€",
                List.of(Tartu),
                List.of(Scooter),
                true,
                new Money("3€")
        );

        CityToVehicleRule tartuBikeFee = new CityToVehicleRule(
                "Regional base bike fee in Tartu: 2.5€",
                List.of(Tartu),
                List.of(Bike),
                true,
                new Money("2.5€")
        );

        CityToVehicleRule parnuCarFee = new CityToVehicleRule(
                "Regional base car fee in Pärnu: 3€",
                List.of(Parnu),
                List.of(Car),
                true,
                new Money("3€")
        );

        CityToVehicleRule parnuScooterFee = new CityToVehicleRule(
                "Regional base scooter fee in Pärnu: 2.5€",
                List.of(Parnu),
                List.of(Scooter),
                true,
                new Money("2.5€")
        );

        CityToVehicleRule parnuBikeFee = new CityToVehicleRule(
                "Regional base bike fee in Pärnu: 2€",
                List.of(Parnu),
                List.of(Bike),
                true,
                new Money("2€")
        );

        cityToVehicleRuleService.saveAll(List.of(
                tallinnCarFee, tallinnScooterFee, tallinnBikeFee,
                tartuCarFee, tartuScooterFee, tartuBikeFee,
                parnuCarFee, parnuScooterFee, parnuBikeFee
        ));
    }
}
