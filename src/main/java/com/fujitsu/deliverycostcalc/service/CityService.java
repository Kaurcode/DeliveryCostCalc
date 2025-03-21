package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.repository.CityRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public interface CityService extends CrudService<City, CityRepository> {
    void deleteCityById(Long weatherDataId);

    Optional<City> getCityByStationName(String stationName);
    HashMap<String, City> getCitiesMappedByStationName();
    HashSet<String> getStationNamesAsSet();
}
