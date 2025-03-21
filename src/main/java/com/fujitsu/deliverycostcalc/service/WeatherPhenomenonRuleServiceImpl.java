package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.WeatherPhenomenonRule;
import com.fujitsu.deliverycostcalc.repository.WeatherPhenomenonRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherPhenomenonRuleServiceImpl
        extends CrudServiceImpl<WeatherPhenomenonRule, WeatherPhenomenonRuleRepository>
        implements WeatherPhenomenonRuleService {

    public WeatherPhenomenonRuleServiceImpl(WeatherPhenomenonRuleRepository repository) {
        super(repository);
    }
}
