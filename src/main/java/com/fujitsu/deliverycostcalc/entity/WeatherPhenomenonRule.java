package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.Phenomenon;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="WEATHER_PHENOMENON_RULE")
public class WeatherPhenomenonRule implements WeatherRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="DESCRIPTION")
    String description;

    @OneToMany(
            mappedBy = "rule",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    List<PhenomenonRuleMapping> phenomenons;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "VEHICLES_WEATHER_PHENOMENON_RULES",
            joinColumns = {
                    @JoinColumn(name = "WEATHER_PHENOMENON_RULE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "VEHICLE_ID")
            }
    )
    List<Vehicle> vehicles;

    @Column(name="IS_ALLOWED", nullable=false)
    boolean isAllowed;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "cents", column = @Column(name = "MONEY_IN_CENTS"))})
    Money money;
}
