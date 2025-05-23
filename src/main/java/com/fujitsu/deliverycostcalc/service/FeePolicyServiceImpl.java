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

    /**
     * Returns all policies as an arraylist from the database
     * @return A list of all policies
     */
    @Override
    public List<FeePolicy> getAllPolicies() {
        List<FeePolicy> policies = new ArrayList<>();

        policies.addAll(cityToVehicleRuleService.findAll());
        policies.addAll(weatherPhenomenonRuleService.findAll());
        policies.addAll(weatherRangeRuleService.findAll());

        return policies;
    }

    /**
     * Checks all policies in the database and then calculates the fee based on the data
     * @param data Data based on which the fee is calculated on
     * @return The fee that was calculated based on data, an empty optional if the result isn't allowed
     * @throws InvalidMoneyException Shouldn't actually be thrown, check Money class constructor if it is
     */
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

    /**
     * Returns the number of total policies in the database
     * @return The number of total policies in the database
     */
    @Override
    public long count() {
        return cityToVehicleRuleService.count() +
                weatherPhenomenonRuleService.count() +
                weatherRangeRuleService.count();
    }

    /**
     * Returns if there are no policies in the database
     * @return true if there are no policies in the database, otherwise false
     */
    @Override
    public boolean isEmpty() {
        return count() == 0;
    }
}
