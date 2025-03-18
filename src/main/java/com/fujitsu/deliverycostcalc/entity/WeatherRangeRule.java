package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="WEATHER_RANGE_RULE")
public class WeatherRangeRule implements WeatherRule{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="WEATHER_FIELD_TYPE", nullable=false)
    private WeatherFieldType weatherFieldType;

    @Column(name="IS_START_INCLUSIVE", nullable=false)
    private boolean isStartInclusive;
    @Column(name="START_VALUE", nullable=false)
    private float startValue;

    @Column(name="IS_END_INCLUSIVE", nullable=false)
    private boolean isEndInclusive;
    @Column(name="END_VALUE", nullable=false)
    private float endValue;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "VEHICLES_WEATHER_RANGE_RULES",
            joinColumns = {
                    @JoinColumn(name = "WEATHER_RANGE_RULE_ID")
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
