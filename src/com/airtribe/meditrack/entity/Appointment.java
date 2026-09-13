package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment extends MedicalEntity implements Cloneable {
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;

    public Appointment(String id, Patient patient, Doctor doctor, LocalDateTime appointmentDateTime, AppointmentStatus status) {
        super(id);
        this.patient = Objects.requireNonNull(patient);
        this.doctor = Objects.requireNonNull(doctor);
        this.appointmentDateTime = Objects.requireNonNull(appointmentDateTime);
        this.status = Objects.requireNonNull(status);
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @Override
    public Bill generateBill() {
        return doctor.generateBill();
    }

    @Override
    public Appointment clone() {
        return new Appointment(
                getId(),
                patient.clone(),
                doctor.clone(),
                appointmentDateTime,
                status
        );
    }

    @Override
    public String toString() {
        return "Appointment{id='" + getId() + "', patient=" + patient.getName() + ", doctor=" + doctor.getName()
                + ", dateTime=" + appointmentDateTime + ", status=" + status + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Appointment other)) return false;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
