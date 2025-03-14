package com.fujitsu.deliverycostcalc;

import java.util.ArrayList;
import java.util.Map;

public class City {
    private String name;
    private String stationName;
    private String wmocode;

    private Map<VehicleType, Money> vehicleToMoneyMap;

    private ArrayList<WeatherData> weatherDataList;

    public City(String name, String stationName) {
        this.name = name;
        this.stationName = stationName;

        this.weatherDataList = new ArrayList<>();
    }

    public String getStationName() {
        return stationName;
    }

    public void refreshWMOCode(String wmocode) {
        if (this.wmocode != null && this.wmocode.equals(wmocode)) {
            return;
        }

        System.out.printf("Refreshing WMO code for %s, old WMO code is: %s; new WMO code is: %s",
                name, this.wmocode, wmocode);
        this.wmocode = wmocode;
    }

    public void addWeatherData(WeatherData weatherData) {
        this.weatherDataList.add(weatherData);
    }
}
