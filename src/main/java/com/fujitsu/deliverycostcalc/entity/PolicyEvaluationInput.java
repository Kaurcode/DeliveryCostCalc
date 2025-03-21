package com.fujitsu.deliverycostcalc.entity;

public class PolicyEvaluationInput {
    private final City city;
    private final Vehicle vehicle;
    private final WeatherData weatherData;

    public PolicyEvaluationInput(City city, Vehicle vehicle, WeatherData weatherData) {
        this.city = city;
        this.vehicle = vehicle;
        this.weatherData = weatherData;
    }

    public City getCity() {
        return city;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }
}
