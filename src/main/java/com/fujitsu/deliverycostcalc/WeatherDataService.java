package com.fujitsu.deliverycostcalc;

import java.util.List;
import java.util.Optional;

public interface WeatherDataService {
    WeatherData saveWeatherData(WeatherData weatherData);
    List<WeatherData> fetchWeatherDataList();
    void deleteWeatherDataById(Long weatherDataId);

    Optional<WeatherData> getLatestWeatherDataByCity(City city);
}
