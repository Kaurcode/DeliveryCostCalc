package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.entity.WeatherData;
import com.fujitsu.deliverycostcalc.repository.WeatherDataRepository;

import java.util.Optional;

public interface WeatherDataService extends CrudService<WeatherData, WeatherDataRepository> {
    void deleteWeatherDataById(Long weatherDataId);

    Optional<WeatherData> getLatestWeatherDataByCity(City city);
}
