package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl extends CrudServiceImpl<City, CityRepository> implements CityService {
    public CityServiceImpl(CityRepository cityRepository) {
        super(cityRepository);
    }

    @Override
    public void deleteCityById(Long weatherDataId) {
        repository.deleteById(weatherDataId);
    }

    @Override
    public Optional<City> getCityByStationName(String stationName) {
        return repository.findByStationName(stationName);
    }

    @Override
    public Optional<City> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public HashMap<String, City> getCitiesMappedByStationName() {
        List<City> cities = findAll();
        return cities
                .stream()
                .collect(
                        Collectors.toMap(
                                City::getStationName,
                                city -> city,
                                (existing, replacement) -> existing, HashMap::new
                        ));
    }

    @Override
    public HashSet<String> getStationNamesAsSet() {
        List<City> cities = findAll();
        return cities
                .stream()
                .map(City::getStationName)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
