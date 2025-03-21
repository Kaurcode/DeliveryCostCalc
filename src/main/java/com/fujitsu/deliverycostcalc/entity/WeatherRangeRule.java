package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="WEATHER_RANGE_RULE")
public abstract class WeatherRangeRule implements FeePolicy {
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

    public boolean isStartInclusive() {
        return isStartInclusive;
    }

    public float getStartValue() {
        return startValue;
    }

    public boolean isEndInclusive() {
        return isEndInclusive;
    }

    public float getEndValue() {
        return endValue;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public abstract double getWeatherValue(WeatherData data);

    @Override
    public boolean appliesTo(PolicyEvaluationInput data) {
        double value = getWeatherValue(data.getWeatherData());

        if (value < getStartValue()) {
            return false;
        }

        if (getEndValue() < value) {
            return false;
        }

        if (value == getStartValue() && !isStartInclusive()) {
            return false;
        }

        if (value == getEndValue() && !isEndInclusive()) {
            return false;
        }

        return true;
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
