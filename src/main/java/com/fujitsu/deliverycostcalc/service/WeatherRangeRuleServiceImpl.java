package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.WeatherRangeRule;
import com.fujitsu.deliverycostcalc.repository.WeatherRangeRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherRangeRuleServiceImpl extends CrudServiceImpl<WeatherRangeRule, WeatherRangeRuleRepository>
        implements WeatherRangeRuleService {

    public WeatherRangeRuleServiceImpl(WeatherRangeRuleRepository repository) {
        super(repository);
    }
}
