package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CityToVehicleRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "CITIES_TO_VEHICLE_RULES",
            joinColumns = {
                    @JoinColumn(name = "RULE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "CITY_ID")
            }
    )
    private List<City> cities;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "VEHICLES_TO_VEHICLE_RULES",
            joinColumns = {
                    @JoinColumn(name = "RULE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "VEHICLE_ID")
            }
    )
    private List<Vehicle> vehicles;

    @Column(name = "IS_ALLOWED", nullable = false)
    private boolean isAllowed;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "cents", column = @Column(name = "MONEY_IN_CENTS"))})
    private Money money;
}
