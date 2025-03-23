package com.fujitsu.deliverycostcalc.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhenomenonTest {
    @Test
    @DisplayName("Should return STORMY for weather phenomenons related to glaze, hail or thunder")
    void shouldReturnStormyPhenomenon() {
        assertEquals(Phenomenon.STORMY, Phenomenon.parsePhenomenon("Glaze"));
        assertEquals(Phenomenon.STORMY, Phenomenon.parsePhenomenon("Hail"));
        assertEquals(Phenomenon.STORMY, Phenomenon.parsePhenomenon("Thunderstorm"));
    }

    @Test
    @DisplayName("Should return SNOWY for weather phenomenons related to snow or sleet")
    void shouldReturnSnowyPhenomenon() {
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Light snow shower"));
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Moderate snow shower"));
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Heavy snow shower"));
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Light sleet"));
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Moderate sleet"));
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Light snowfall"));
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Moderate snowfall"));
        assertEquals(Phenomenon.SNOWY, Phenomenon.parsePhenomenon("Heavy snowfall"));
    }

    @Test
    @DisplayName("Should return RAINY for weather phenomenons related to rain")
    void shouldReturnRainyPhenomenon() {
        assertEquals(Phenomenon.RAINY, Phenomenon.parsePhenomenon("Light shower"));
        assertEquals(Phenomenon.RAINY, Phenomenon.parsePhenomenon("Moderate shower"));
        assertEquals(Phenomenon.RAINY, Phenomenon.parsePhenomenon("Heavy shower"));
        assertEquals(Phenomenon.RAINY, Phenomenon.parsePhenomenon("Light rain"));
        assertEquals(Phenomenon.RAINY, Phenomenon.parsePhenomenon("Moderate rain"));
        assertEquals(Phenomenon.RAINY, Phenomenon.parsePhenomenon("Heavy rain"));
    }

    @Test
    @DisplayName("Should return OTHER for unrelated weather phenomenons")
    void shouldReturnOtherPhenomenon() {
        assertEquals(Phenomenon.OTHER, Phenomenon.parsePhenomenon("Clear"));
        assertEquals(Phenomenon.OTHER, Phenomenon.parsePhenomenon("Few clouds"));
        assertEquals(Phenomenon.OTHER, Phenomenon.parsePhenomenon("Variable clouds"));
        assertEquals(Phenomenon.OTHER, Phenomenon.parsePhenomenon("Cloudy with clear spells"));
        assertEquals(Phenomenon.OTHER, Phenomenon.parsePhenomenon("Overcast"));
        assertEquals(Phenomenon.OTHER, Phenomenon.parsePhenomenon("Mist"));
        assertEquals(Phenomenon.OTHER, Phenomenon.parsePhenomenon("Fog"));
    }
}
