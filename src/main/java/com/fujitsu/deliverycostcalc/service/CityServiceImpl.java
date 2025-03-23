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

    /**
     * Deletes a City instance with the provided ID
     * @param weatherDataId The ID of a City instance which will be deleted
     */
    @Override
    public void deleteCityById(Long weatherDataId) {
        repository.deleteById(weatherDataId);
    }

    /**
     * Gets a City instance by its weather station name
     * @param stationName The weather station name which is used to search for a City instance
     * @return An Optional of a City if a city was found or an empty Optional otherwise
     */
    @Override
    public Optional<City> getCityByStationName(String stationName) {
        return repository.findByStationName(stationName);
    }

    /**
     * Finds a city by its name
     * @param name Name of the city which is looked for
     * @return Optional of a City if the city was found or an empty Optional otherwise
     */
    @Override
    public Optional<City> findByName(String name) {
        return repository.findByName(name);
    }

    /**
     * Gets a HashMap where the keys are the string names of weather station names and values are City class instances
     * @return A hashmap of station names and City class instances
     */
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

    /**
     * Gets all the station names as a set
     * @return All the station names as a HashSet
     */
    @Override
    public HashSet<String> getStationNamesAsSet() {
        List<City> cities = findAll();
        return cities
                .stream()
                .map(City::getStationName)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
