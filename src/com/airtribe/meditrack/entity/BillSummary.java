package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public final class BillSummary {
    private final String patientId;
    private final double totalAmount;
    private final LocalDateTime generatedAt;

    public BillSummary(String patientId, double totalAmount, LocalDateTime generatedAt) {
        this.patientId = patientId;
        this.totalAmount = totalAmount;
        this.generatedAt = generatedAt;
    }

    public String getPatientId() {
        return patientId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}

