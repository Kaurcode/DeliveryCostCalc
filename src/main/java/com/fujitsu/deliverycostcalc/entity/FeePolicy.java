package com.fujitsu.deliverycostcalc.entity;

public interface FeePolicy {
    boolean appliesTo(PolicyEvaluationInput data);
    boolean isAllowed();
    Money getMoney();
}
