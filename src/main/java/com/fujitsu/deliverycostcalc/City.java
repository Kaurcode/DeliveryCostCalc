package com.fujitsu.deliverycostcalc;

import java.util.Map;

public class City {
    private String name;
    private String wmocode;

    Map<VehicleType, Money> vehicleToMoneyMap;

    WeatherData latestWeatherData;
}
