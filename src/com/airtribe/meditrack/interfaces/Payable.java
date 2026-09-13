package com.airtribe.meditrack.interfaces;

public interface Payable {
    double getAmount();

    default double calculateTax(double taxRate) {
        return getAmount() * taxRate;
    }

    default double calculateTotal(double taxRate) {
        return getAmount() + calculateTax(taxRate);
    }
}
