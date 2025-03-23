package com.fujitsu.deliverycostcalc.entity;

/**
 * A record which is used to feed data into the FeePolicy interface appliesTo methods
 */
public record PolicyEvaluationInput(City city, Vehicle vehicle, WeatherData weatherData) {
}
