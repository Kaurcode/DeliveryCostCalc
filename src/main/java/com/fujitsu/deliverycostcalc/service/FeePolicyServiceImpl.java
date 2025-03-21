package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.FeePolicy;
import com.fujitsu.deliverycostcalc.entity.Money;
import com.fujitsu.deliverycostcalc.entity.PolicyEvaluationInput;
import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeePolicyServiceImpl implements FeePolicyService {
    private final CityToVehicleRuleService cityToVehicleRuleService;
    private final WeatherPhenomenonRuleService weatherPhenomenonRuleService;
    private final WeatherRangeRuleService weatherRangeRuleService;

    public FeePolicyServiceImpl(CityToVehicleRuleService cityToVehicleRuleService,
                                WeatherPhenomenonRuleService weatherPhenomenonRuleService,
                                WeatherRangeRuleService weatherRangeRuleService) {

        this.cityToVehicleRuleService = cityToVehicleRuleService;
        this.weatherPhenomenonRuleService = weatherPhenomenonRuleService;
        this.weatherRangeRuleService = weatherRangeRuleService;
    }

    @Override
    public List<FeePolicy> getAllPolicies() {
        List<FeePolicy> policies = new ArrayList<>();

        policies.addAll(cityToVehicleRuleService.findAll());
        policies.addAll(weatherPhenomenonRuleService.findAll());
        policies.addAll(weatherRangeRuleService.findAll());

        return policies;
    }

    @Override
    public Optional<Money> calculateFee(PolicyEvaluationInput data) throws InvalidMoneyException {
        Money money = new Money("0");

        for (FeePolicy policy : getAllPolicies()) {
            if (!policy.appliesTo(data)) continue;
            if (!policy.isAllowed()) {
                return Optional.empty();
            }
            money.add(policy.getMoney());
        }

        return Optional.of(money);
    }

    @Override
    public long count() {
        return cityToVehicleRuleService.count() +
                weatherPhenomenonRuleService.count() +
                weatherRangeRuleService.count();
    }

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }
}
