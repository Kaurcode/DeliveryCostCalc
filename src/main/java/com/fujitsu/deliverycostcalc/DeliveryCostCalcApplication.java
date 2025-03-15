package com.fujitsu.deliverycostcalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DeliveryCostCalcApplication {
    private static final Map<String, String> CITIES_TO_STATION_NAMES = Map.of(
            "Tallinn", "Tallinn-Harku",
            "Tartu", "Tartu-Tõravere",
            "Pärnu", "Pärnu"
    );

    private static HashMap<String, City> generateCities() {
        HashMap<String, City> cities = new HashMap<>();

        for (Map.Entry<String, String> entry : CITIES_TO_STATION_NAMES.entrySet()) {
            City city = new City(entry.getKey(), entry.getValue());
            cities.put(entry.getValue(), city);
        }

        return cities;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        SpringApplication.run(DeliveryCostCalcApplication.class, args);

        HashMap<String, City> stationsToCitiesMap = generateCities();

        WeatherDataFetcher.readXML(stationsToCitiesMap);

        for (City city : stationsToCitiesMap.values()) {
            System.out.println(city.toString());
        }
    }

}
