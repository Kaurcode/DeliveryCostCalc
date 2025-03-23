package com.fujitsu.deliverycostcalc.entity;

public interface FeePolicy {
    /**
     * Checks if the current policy can be applied to the data given
     * @param data Data which contains the City, Vehicle and WeatherData instances
     * @return If the current policy matches the data
     */
    boolean appliesTo(PolicyEvaluationInput data);

    /**
     * Checks if the policy allows such an event to occur
     * @return Is the event allowed?
     */
    boolean isAllowed();

    /**
     * Gets the amount of money that is added to the fee if the policy is applied
     * @return Money instance
     */
    Money getMoney();
}
