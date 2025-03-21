package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.Vehicle;
import com.fujitsu.deliverycostcalc.repository.VehicleRepository;

import java.util.HashSet;
import java.util.Optional;

public interface VehicleService extends CrudService<Vehicle, VehicleRepository> {
    Optional<Vehicle> findByType(String type);
    HashSet<String> getVehicleTypesAsSet();
}
