package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.FeePolicy;
import com.fujitsu.deliverycostcalc.entity.Money;
import com.fujitsu.deliverycostcalc.entity.PolicyEvaluationInput;
import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;

import java.util.List;
import java.util.Optional;

public interface FeePolicyService {
    List<FeePolicy> getAllPolicies();
    Optional<Money> calculateFee(PolicyEvaluationInput data) throws InvalidMoneyException;
    long count();
    boolean isEmpty();
}
