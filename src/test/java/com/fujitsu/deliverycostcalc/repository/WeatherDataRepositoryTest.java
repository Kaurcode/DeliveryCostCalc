package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.entity.WeatherData;
import com.fujitsu.deliverycostcalc.entity.Phenomenon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WeatherDataRepositoryTest {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    void getWeatherDataByCity_shouldReturnLatestWeatherDataInOrder() {
        City city = new City("Tallinn", "Tallinn-Harku");
        cityRepository.save(city);

        WeatherData w1 = new WeatherData("1742600000", "5.0", "2.0", "Clear", city, false, null);
        WeatherData w2 = new WeatherData("1742700000", "6.0", "3.0", "Rain", city, false, null);
        WeatherData w3 = new WeatherData("1742800000", "7.0", "4.0", "Snow", city, false, null);

        weatherDataRepository.saveAll(List.of(w1, w2, w3));

        List<WeatherData> latest = weatherDataRepository.getWeatherDataByCity(city, PageRequest.of(0, 2));

        assertEquals(2, latest.size());
        assertTrue(latest.get(0).getTimestamp().isAfter(latest.get(1).getTimestamp()));
        assertEquals(Phenomenon.SNOWY, latest.get(0).getPhenomenon());
    }
}