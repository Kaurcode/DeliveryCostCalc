package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.CityToVehicleRule;
import com.fujitsu.deliverycostcalc.repository.CityToVehicleRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class CityToVehicleRuleServiceImpl extends CrudServiceImpl<CityToVehicleRule, CityToVehicleRuleRepository>
        implements CityToVehicleRuleService {

    public CityToVehicleRuleServiceImpl(CityToVehicleRuleRepository repository) {
        super(repository);
    }
}
