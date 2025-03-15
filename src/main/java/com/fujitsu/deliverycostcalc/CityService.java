package com.fujitsu.deliverycostcalc;

import java.util.List;
import java.util.Optional;

public interface CityService {
    City saveCity(City city);
    List<City> fetchCityList();
    void deleteCityById(Long weatherDataId);

    Optional<City> getCityByStationName(String stationName);
}
