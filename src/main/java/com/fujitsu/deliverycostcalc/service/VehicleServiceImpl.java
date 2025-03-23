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

    /**
     * Finds a Vehicle instance from the database by type (type is a unique value in the database)
     * @param type Vehicle type for which a vehicle is searched for
     * @return Optional instance of the vehicle or an empty optional if no vehicle with such type is found
     */
    @Override
    public Optional<Vehicle> findByType(String type) {
        return repository.findByType(type);
    }

    /**
     * Gets all saved vehicle types (in database) as a set
     * @return All saved vehicle types as a set
     */
    @Override
    public HashSet<String> getVehicleTypesAsSet() {
        return findAll()
                .stream()
                .map(Vehicle::getType)
                .collect(Collectors.toCollection(HashSet<String>::new));
    }
}
