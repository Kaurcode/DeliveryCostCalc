package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    Optional<City> findByStationName(String stationName);
}
