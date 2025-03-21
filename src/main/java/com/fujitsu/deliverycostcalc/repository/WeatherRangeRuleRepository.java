package com.fujitsu.deliverycostcalc.repository;

import com.fujitsu.deliverycostcalc.entity.WeatherRangeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRangeRuleRepository extends JpaRepository<WeatherRangeRule, Long> {
}
