package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByStationName(String stationName);
}
