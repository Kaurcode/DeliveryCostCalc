package com.fujitsu.deliverycostcalc;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="WEATHER_DATA")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CITY_ID", nullable=false)
    private City city;

    @Column(name="TIMESTAMP", nullable=false)
    private Instant timestamp;
    @Column(name="AIR_TEMPERATURE", nullable=false)
    private double airTemperature;
    @Column(name="WIND_SPEED", nullable=false)
    private double windSpeed;

    @Column(name="PHENOMENON", nullable=false)
    @Enumerated(EnumType.STRING)
    private Phenomenon phenomenon;

    protected WeatherData() {}

    public WeatherData(String timestamp, String airTemperature, String windSpeed, String phenomenon) {
        this.timestamp = Instant.ofEpochSecond(Long.parseLong(timestamp));
        this.airTemperature = Double.parseDouble(airTemperature);
        this.windSpeed = Double.parseDouble(windSpeed);
        this.phenomenon = Phenomenon.parsePhenomenon(phenomenon);
    }

    public void setCity(City city) {
        this.city = city;
    }
}
