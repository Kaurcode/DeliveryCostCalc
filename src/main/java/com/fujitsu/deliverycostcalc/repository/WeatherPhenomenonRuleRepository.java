package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.WeatherPhenomenonRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherPhenomenonRuleRepository extends JpaRepository<WeatherPhenomenonRule, Long> {
}
