package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.Money;
import com.fujitsu.deliverycostcalc.VehicleType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Transient
    private Map<VehicleType, Money> vehicleToMoneyMap;

    @OneToMany(mappedBy="city", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<WeatherData> weatherDataList = new ArrayList<>();

    protected City() {}

    public City(String name, String stationName) {
        this.name = name;
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

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
