package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> fetchCityList() {
        return (List<City>) cityRepository.findAll();
    }

    @Override
    public void deleteCityById(Long weatherDataId) {
        cityRepository.deleteById(weatherDataId);
    }

    @Override
    public Optional<City> getCityByStationName(String stationName) {
        return cityRepository.findByStationName(stationName);
    }

    @Override
    public HashMap<String, City> getCitiesMappedByStationName() {
        List<City> cities = fetchCityList();
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
        List<City> cities = fetchCityList();
        return cities
                .stream()
                .map(City::getStationName)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
