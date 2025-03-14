package com.fujitsu.deliverycostcalc;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDataRepository extends CrudRepository<WeatherData, Long> {
    @Query("SELECT w FROM WeatherData w WHERE w.city = :city ORDER BY w.timestamp DESC")
    List<WeatherData> getWeatherDataByCity(@Param("city") City city, Pageable pageable);
}
