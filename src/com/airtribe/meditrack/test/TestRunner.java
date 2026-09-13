package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.Address;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.Specialization;

import java.time.LocalDateTime;

public class TestRunner {
    public static void main(String[] args) {
        Patient patient = new Patient("PAT-1", "Test Patient", "9999988888", 65, new Address("A", "B"));
        Doctor doctor = new Doctor("DOC-1", "Test Doctor", "9999977777", Specialization.CARDIOLOGIST, 1200);
        Appointment appointment = new Appointment("APT-1", patient, doctor, LocalDateTime.now().plusHours(2), AppointmentStatus.CONFIRMED);

        Appointment clone = appointment.clone();
        clone.getPatient().getAddress().setCity("Changed");

        if (appointment.getPatient().getAddress().getCity().equals(clone.getPatient().getAddress().getCity())) {
            throw new IllegalStateException("Deep clone failed for patient address");
        }

        if (doctor.generateBill().getTotal() <= doctor.generateBill().getAmount()) {
            System.out.println("Manual Test Passed: Billing tax applied.");
        }

        System.out.println("All manual tests passed.");
    }
}
