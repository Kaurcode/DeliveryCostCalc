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
public class WeatherDataServiceImpl extends CrudServiceImpl<WeatherData, WeatherDataRepository>
        implements WeatherDataService {

    public WeatherDataServiceImpl(WeatherDataRepository weatherDataRepository) {
        super(weatherDataRepository);
    }

    @Override
    public void deleteWeatherDataById(Long weatherDataId) {
        repository.deleteById(weatherDataId);
    }

    @Override
    public Optional<WeatherData> getLatestWeatherDataByCity(City city) {
        Pageable limitOne = PageRequest.of(0, 1);
        List<WeatherData> result = repository.getWeatherDataByCity(city, limitOne);
        return result.stream().findFirst();
    }
}
