package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CityToVehicleRule implements FeePolicy {
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

    protected CityToVehicleRule() {}

    public CityToVehicleRule(
            String description, List<City> cities, List<Vehicle> vehicles, boolean isAllowed, Money money
    ) {
        this.description = description;
        this.cities = cities;
        this.vehicles = vehicles;
        this.isAllowed = isAllowed;
        this.money = money;
    }

    @Override
    public boolean appliesTo(PolicyEvaluationInput data) {
        return cities.contains(data.getCity()) &&
                vehicles.contains(data.getVehicle());
    }

    @Override
    public boolean isAllowed() {
        return isAllowed;
    }

    @Override
    public Money getMoney() {
        return money;
    }


}
