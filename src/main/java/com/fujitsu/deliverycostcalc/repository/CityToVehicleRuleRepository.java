package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.CityToVehicleRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityToVehicleRuleRepository extends JpaRepository<CityToVehicleRule, Long> {
}
