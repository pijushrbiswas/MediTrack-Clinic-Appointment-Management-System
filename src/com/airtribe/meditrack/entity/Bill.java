package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;

import java.io.Serializable;

public class Bill implements Payable, Serializable {
    private final double amount;
    private final double tax;
    private final double total;
    private final String description;

    public Bill(double amount, String description) {
        this.amount = amount;
        this.description = description;
        this.tax = calculateTax(Constants.TAX_RATE);
        this.total = calculateTotal(Constants.TAX_RATE);
    }

    @Override
    public double getAmount() {
        return amount;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Bill{amount=" + amount + ", tax=" + tax + ", total=" + total + ", description='" + description + "'}";
    }
}

