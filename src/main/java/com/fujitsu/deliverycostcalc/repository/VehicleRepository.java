package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByType(String type);
}
