package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.City;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface CityService {
    City saveCity(City city);
    List<City> fetchCityList();
    void deleteCityById(Long weatherDataId);

    Optional<City> getCityByStationName(String stationName);
    HashMap<String, City> getCitiesMappedByStationName();
    HashSet<String> getStationNamesAsSet();
}
