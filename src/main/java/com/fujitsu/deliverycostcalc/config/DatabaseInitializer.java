package com.fujitsu.deliverycostcalc.config;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.service.CityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private static final Map<String, String> DEFAULT_CITIES_TO_STATION_NAMES = Map.of(
            "Tallinn", "Tallinn-Harku",
            "Tartu", "Tartu-Tõravere",
            "Pärnu", "Pärnu"
    );

    private final CityService cityService;

    public DatabaseInitializer(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void run(String... args) {
        ensureDefaultConfiguration();
    }

    private void ensureDefaultConfiguration() {
        ensureDefaultCitiesExist();
    }

    private void ensureDefaultCitiesExist() {
        HashSet<String> stationNames = cityService.getStationNamesAsSet();

        for (Map.Entry<String, String> entry : DEFAULT_CITIES_TO_STATION_NAMES.entrySet()) {
            String cityName = entry.getKey();
            String stationName = entry.getValue();

            if (!stationNames.contains(stationName)) {
                City city = new City(cityName, stationName);
                cityService.saveCity(city);
            }
        }
    }
}
