package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.entity.WeatherData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    /**
     * Returns a list of WeatherData entries for the specified city, ordered by timestamp in descending order
     * (most recent first)
     * @param city The City for which weather data is requested
     * @param pageable A Pageable object to limit the number of results
     * @return A list of WeatherData instances ordered by timestamp in descending order
     */
    @Query("SELECT w FROM WeatherData w WHERE w.city = :city ORDER BY w.timestamp DESC")
    List<WeatherData> getWeatherDataByCity(@Param("city") City city, Pageable pageable);
}
