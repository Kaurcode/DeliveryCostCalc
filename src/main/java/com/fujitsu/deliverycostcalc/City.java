package com.fujitsu.deliverycostcalc;

import java.util.ArrayList;
import java.util.Map;

public class City {
    private String name;
    private String wmocode;

    Map<VehicleType, Money> vehicleToMoneyMap;

    ArrayList<WeatherData> weatherDataList;

    public City(String name, String wmocode) {
        this.name = name;
        this.wmocode = wmocode;

        this.weatherDataList = new ArrayList<>();
    }
}
