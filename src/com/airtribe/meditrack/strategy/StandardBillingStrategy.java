package com.airtribe.meditrack.strategy;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public double apply(double baseAmount) {
        return baseAmount;
    }
}

