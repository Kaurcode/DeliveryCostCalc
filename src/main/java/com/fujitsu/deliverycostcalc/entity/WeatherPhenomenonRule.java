package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="WEATHER_PHENOMENON_RULE")
public class WeatherPhenomenonRule implements FeePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(
            mappedBy = "rule",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    private List<PhenomenonRuleMapping> phenomenons = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "VEHICLES_WEATHER_PHENOMENON_RULES",
            joinColumns = {
                    @JoinColumn(name = "WEATHER_PHENOMENON_RULE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "VEHICLE_ID")
            }
    )
    private List<Vehicle> vehicles = new ArrayList<>();

    @Column(name="IS_ALLOWED", nullable=false)
    private boolean isAllowed;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "cents", column = @Column(name = "MONEY_IN_CENTS"))})
    private Money money;

    protected WeatherPhenomenonRule() {}

    public WeatherPhenomenonRule(String description, List<Phenomenon> phenomenons, List<Vehicle> vehicles, boolean isAllowed, Money money) {
        this.description = description;
        this.phenomenons = new ArrayList<>();

        for (Phenomenon phenomenon : phenomenons) {
            this.phenomenons.add(new PhenomenonRuleMapping(phenomenon, this));
        }

        this.vehicles = vehicles;
        this.isAllowed = isAllowed;
        this.money = money;
    }

    private List<Phenomenon> getPhenomenons() {
        List<Phenomenon> phenomenons = new ArrayList<>();

        for (PhenomenonRuleMapping phenomenon : this.phenomenons) {
            phenomenons.add(phenomenon.getPhenomenon());
        }

        return phenomenons;
    }

    @Override
    public boolean appliesTo(PolicyEvaluationInput data) {
        return vehicles.contains(data.vehicle()) &&
                getPhenomenons().contains(data.weatherData().getPhenomenon());
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
