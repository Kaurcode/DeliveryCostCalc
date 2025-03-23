package com.fujitsu.deliverycostcalc.controller;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.entity.Money;
import com.fujitsu.deliverycostcalc.entity.Vehicle;
import com.fujitsu.deliverycostcalc.entity.WeatherData;
import com.fujitsu.deliverycostcalc.service.CityService;
import com.fujitsu.deliverycostcalc.service.FeePolicyService;
import com.fujitsu.deliverycostcalc.service.VehicleService;
import com.fujitsu.deliverycostcalc.service.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryFeeCalcController.class)
public class DeliveryFeeCalcControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CityService cityService;
    @MockitoBean
    private VehicleService vehicleService;
    @MockitoBean
    private FeePolicyService feePolicyService;
    @MockitoBean
    private WeatherDataService weatherDataService;

    @Test
    public void shouldReturnDeliveryFee_whenDataIsValid() throws Exception {
        City mockCity = new City("Tallinn", "Tallinn-Harku");
        Vehicle mockVehicle = new Vehicle("Car");
        WeatherData mockWeatherData = Mockito.mock(WeatherData.class);
        Money mockFee = new Money("4€");

        Mockito.when(mockWeatherData.isErroneous()).thenReturn(false);
        Mockito.when(cityService.findByName("Tallinn")).thenReturn(Optional.of(mockCity));
        Mockito.when(vehicleService.findByType("Car")).thenReturn(Optional.of(mockVehicle));
        Mockito.when(weatherDataService.getLatestWeatherDataByCity(mockCity)).thenReturn(Optional.of(mockWeatherData));
        Mockito.when(feePolicyService.calculateFee(any())).thenReturn(Optional.of(mockFee));

        mockMvc.perform(get("/deliveryFee?city=Tallinn&vehicle=Car"))
                .andExpect(status().isOk())
                .andExpect(content().string(new Money("4€").toString()));
    }

    @Test
    public void shouldReturn404_whenCityNotFound() throws Exception {
        Mockito.when(cityService.findByName("InvalidCity")).thenReturn(Optional.empty());

        mockMvc.perform(get("/deliveryFee?city=InvalidCity&vehicle=Car"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn400_whenWeatherDataIsErroneous() throws Exception {
        City mockCity = new City("Tallinn", "Tallinn-Harku");
        Vehicle mockVehicle = new Vehicle("Car");
        WeatherData mockWeatherData = Mockito.mock(WeatherData.class);

        Mockito.when(mockWeatherData.isErroneous()).thenReturn(true);
        Mockito.when(mockWeatherData.getErrorMessage()).thenReturn("Missing air temperature");
        Mockito.when(cityService.findByName("Tallinn")).thenReturn(Optional.of(mockCity));
        Mockito.when(vehicleService.findByType("Car")).thenReturn(Optional.of(mockVehicle));
        Mockito.when(weatherDataService.getLatestWeatherDataByCity(mockCity)).thenReturn(Optional.of(mockWeatherData));

        mockMvc.perform(get("/deliveryFee?city=Tallinn&vehicle=Car"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Missing air temperature")));
    }

    @Test
    public void shouldReturnForbiddenMessage_whenFeeCalculationReturnsEmpty() throws Exception {
        City mockCity = new City("Tallinn", "Tallinn-Harku");
        Vehicle mockVehicle = new Vehicle("Car");
        WeatherData mockWeatherData = Mockito.mock(WeatherData.class);

        Mockito.when(cityService.findByName("Tallinn")).thenReturn(Optional.of(mockCity));
        Mockito.when(vehicleService.findByType("Car")).thenReturn(Optional.of(mockVehicle));
        Mockito.when(weatherDataService.getLatestWeatherDataByCity(mockCity)).thenReturn(Optional.of(mockWeatherData));
        Mockito.when(mockWeatherData.isErroneous()).thenReturn(false);
        Mockito.when(feePolicyService.calculateFee(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/deliveryFee?city=Tallinn&vehicle=Car"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usage of selected vehicle type is forbidden"));
    }
}
