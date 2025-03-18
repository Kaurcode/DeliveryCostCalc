package com.fujitsu.deliverycostcalc.entity;

import jakarta.persistence.*;

@Entity
@Table(name="VEHICLE")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="TYPE", unique=true, nullable=false)
    private String type;
}
