package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.util.Validator;

public class Doctor extends Person implements Cloneable {
    private Specialization specialization;
    private double consultationFee;

    public Doctor(String id, String name, String phone, Specialization specialization, double consultationFee) {
        super(id, name, phone);
        setSpecialization(specialization);
        setConsultationFee(consultationFee);
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        Validator.requirePositive(consultationFee, "Consultation fee must be positive");
        this.consultationFee = consultationFee;
    }

    @Override
    public Bill generateBill() {
        return new Bill(consultationFee, "Consultation bill for Dr. " + getName());
    }

    @Override
    public Doctor clone() {
        return new Doctor(getId(), getName(), getPhone(), specialization, consultationFee);
    }

    @Override
    public String toString() {
        return "Doctor{id='" + getId() + "', name='" + getName() + "', specialization=" + specialization + ", fee=" + consultationFee + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Doctor other)) return false;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
