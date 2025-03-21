package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="VEHICLE")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="TYPE", unique=true, nullable=false)
    private String type;

    @ManyToMany(
            mappedBy = "vehicles",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    private List<CityToVehicleRule> cityRules = new ArrayList<>();

    @ManyToMany(
            mappedBy = "vehicles",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    private List<WeatherRangeRule> weatherRangeRules = new ArrayList<>();

    @ManyToMany(
            mappedBy = "vehicles",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    private List<WeatherPhenomenonRule> weatherPhenomenonRules;
    private List<WeatherPhenomenonRule> weatherPhenomenonRules = new ArrayList<>();
}
