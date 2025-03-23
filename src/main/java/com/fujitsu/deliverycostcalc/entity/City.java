package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CITY")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME", unique=true, nullable=false)
    private String name;
    @Column(name="STATION_NAME", unique=true, nullable=false)
    private String stationName;
    @Column(name="WMO_CODE")
    private String wmocode;

    @ManyToMany(
            mappedBy = "cities",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    private List<CityToVehicleRule> vehicleRules = new ArrayList<>();

    @OneToMany(mappedBy="city", fetch=FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<WeatherData> weatherDataList = new ArrayList<>();

    protected City() {}

    public City(String name, String stationName) {
        this.name = name;
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    /**
     * Refreshes the WMO (World Meteorological Organization) code of the weather station in the city. Also used for
     * setting the WMO code of the weather station the first time the XML file is read
     * @param wmocode The WMO code of the weather station
     */
    public void refreshWMOCode(String wmocode) {
        if (this.wmocode != null && this.wmocode.equals(wmocode)) {
            return;
        }

        System.out.printf("Refreshing WMO code for %s, old WMO code is: %s; new WMO code is: %s\n",
                name, this.wmocode, wmocode);
        this.wmocode = wmocode;
    }

    public void addWeatherData(WeatherData weatherData) {
        this.weatherDataList.add(weatherData);
        weatherData.setCity(this);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result
                .append("City {")
                .append("\n\tname: ").append(name)
                .append("\n\tstationName: ").append(stationName)
                .append("\n\twmocode: ").append(wmocode)
                .append('\n');

        for (WeatherData weatherData : weatherDataList) {
            result.append("\n").append(weatherData.toString());
        }

        result.append("}\n");

        return result.toString();
    }
}
