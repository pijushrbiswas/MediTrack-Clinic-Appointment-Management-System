package com.airtribe.meditrack.strategy;

public class SeniorCitizenBillingStrategy implements BillingStrategy {
    @Override
    public double apply(double baseAmount) {
        return baseAmount * 0.9;
    }
}

