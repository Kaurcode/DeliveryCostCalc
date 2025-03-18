package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="VEHICLE")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="TYPE", unique=true, nullable=false)
    private String type;

    @ManyToMany(
            mappedBy = "vehicles",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    List<WeatherRangeRule> weatherRangeRules;

    @ManyToMany(
            mappedBy = "vehicles",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    List<WeatherPhenomenonRule> weatherPhenomenonRules;
}
