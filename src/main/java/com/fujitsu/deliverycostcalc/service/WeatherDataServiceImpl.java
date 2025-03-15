package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.entity.WeatherData;
import com.fujitsu.deliverycostcalc.repository.WeatherDataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {
    private final WeatherDataRepository weatherDataRepository;

    public WeatherDataServiceImpl(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @Override
    public WeatherData saveWeatherData(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    @Override
    public List<WeatherData> fetchWeatherDataList() {
        return (List<WeatherData>) weatherDataRepository.findAll();
    }

    @Override
    public void deleteWeatherDataById(Long weatherDataId) {
        weatherDataRepository.deleteById(weatherDataId);
    }

    @Override
    public Optional<WeatherData> getLatestWeatherDataByCity(City city) {
        Pageable limitOne = PageRequest.of(0, 1);
        List<WeatherData> result = weatherDataRepository.getWeatherDataByCity(city, limitOne);
        return result.stream().findFirst();
    }
}
