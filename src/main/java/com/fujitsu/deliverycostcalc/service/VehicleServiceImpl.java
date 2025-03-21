package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.Vehicle;
import com.fujitsu.deliverycostcalc.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl extends CrudServiceImpl<Vehicle, VehicleRepository> implements VehicleService {
    VehicleServiceImpl(VehicleRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Vehicle> findByType(String type) {
        return repository.findByType(type);
    }

    @Override
    public HashSet<String> getVehicleTypesAsSet() {
        return findAll()
                .stream()
                .map(Vehicle::getType)
                .collect(Collectors.toCollection(HashSet<String>::new));
    }
}
