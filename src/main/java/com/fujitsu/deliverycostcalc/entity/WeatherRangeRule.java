package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="WEATHER_RANGE_RULE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "RULE_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class WeatherRangeRule implements FeePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="HAS_START_VALUE", nullable=false)
    private boolean hasStartValue;
    @Column(name="IS_START_INCLUSIVE")
    private boolean isStartInclusive;
    @Column(name="START_VALUE")
    private float startValue;

    @Column(name="HAS_END_VALUE")
    private boolean hasEndValue;
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

    public abstract double getWeatherValue(WeatherData data);

    @Override
    public boolean appliesTo(PolicyEvaluationInput data) {
        double value = getWeatherValue(data.getWeatherData());

        return isStartConditionMet(value) &&
                isEndConditionMet(value);
    }

    private boolean isStartConditionMet(double value) {
        if (!hasStartValue) return true;
        if (startValue < value) return true;
        return value == startValue && isStartInclusive;
    }

    private boolean isEndConditionMet(double value) {
        if (!hasEndValue) return true;
        if (value < endValue) return true;
        return value == endValue && isEndInclusive;
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
