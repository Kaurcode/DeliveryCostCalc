package com.fujitsu.deliverycostcalc.controller;

import com.fujitsu.deliverycostcalc.entity.*;
import com.fujitsu.deliverycostcalc.exception.EmptyXmlTagValueException;
import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import com.fujitsu.deliverycostcalc.service.CityService;
import com.fujitsu.deliverycostcalc.service.FeePolicyService;
import com.fujitsu.deliverycostcalc.service.VehicleService;
import com.fujitsu.deliverycostcalc.service.WeatherDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class DeliveryFeeCalcController {
    private final CityService cityService;
    private final VehicleService vehicleService;
    private final FeePolicyService feePolicyService;
    private final WeatherDataService weatherDataService;

    public DeliveryFeeCalcController(
            CityService cityService,
            VehicleService vehicleService,
            FeePolicyService feePolicyService,
            WeatherDataService weatherDataService)
    {
        this.cityService = cityService;
        this.vehicleService = vehicleService;
        this.feePolicyService = feePolicyService;
        this.weatherDataService = weatherDataService;
    }

    @GetMapping("/deliveryFee")
    public String calculateDeliveryFee(
            @RequestParam(value = "city") String cityName, @RequestParam(value = "vehicle") String vehicleType
    ) throws InvalidMoneyException, EmptyXmlTagValueException {

        City city = cityService.findByName(cityName).orElseThrow();
        Vehicle vehicle = vehicleService.findByType(vehicleType).orElseThrow();
        WeatherData weatherData = weatherDataService.getLatestWeatherDataByCity(city).orElseThrow();

        if (weatherData.isErroneous()) {
            throw new EmptyXmlTagValueException("Data for current time is corrupted:\n" +
                    weatherData.getErrorMessage());
        }

        PolicyEvaluationInput data = new PolicyEvaluationInput(city, vehicle, weatherData);
        Optional<Money> fee = feePolicyService.calculateFee(data);

        if (fee.isEmpty()) {
            return "Usage of selected vehicle type is forbidden";
        }

        return fee.get().toString();
    }
}
