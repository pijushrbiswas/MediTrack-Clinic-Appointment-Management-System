package com.airtribe.meditrack.factory;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.strategy.BillingStrategy;
import com.airtribe.meditrack.strategy.SeniorCitizenBillingStrategy;
import com.airtribe.meditrack.strategy.StandardBillingStrategy;

public final class BillFactory {
    private BillFactory() {
    }

    public static Bill createConsultationBill(Patient patient, double fee) {
        BillingStrategy strategy = patient.getAge() >= 60
                ? new SeniorCitizenBillingStrategy()
                : new StandardBillingStrategy();
        return createConsultationBill(patient, fee, strategy);
    }

    public static Bill createConsultationBill(Patient patient, double fee, BillingStrategy strategy) {
        double finalAmount = strategy.apply(fee);
        return new Bill(finalAmount, "Consultation bill for patient " + patient.getName());
    }
}
