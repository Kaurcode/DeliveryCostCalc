package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="WEATHER_PHENOMENON_RULE")
public class WeatherPhenomenonRule implements WeatherRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(
            mappedBy = "rule",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    private List<PhenomenonRuleMapping> phenomenons;

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
    private List<Vehicle> vehicles;

    @Column(name="IS_ALLOWED", nullable=false)
    private boolean isAllowed;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "cents", column = @Column(name = "MONEY_IN_CENTS"))})
    private Money money;
}
