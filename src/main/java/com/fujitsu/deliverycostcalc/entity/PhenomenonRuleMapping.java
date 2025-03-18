package com.fujitsu.deliverycostcalc.entity;

import com.fujitsu.deliverycostcalc.Phenomenon;
import jakarta.persistence.*;

@Entity
@Table(name = "PHENOMENON_TO_RULE_MAPPING")
public class PhenomenonRuleMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PHENOMENON", nullable = false)
    private Phenomenon phenomenon;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH} )
    @JoinColumn(name = "WEATHER_PHENOMENON_RULE_ID", nullable = false)
    private WeatherPhenomenonRule rule;
}
