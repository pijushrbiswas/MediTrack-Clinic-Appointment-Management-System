package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DoctorService implements Searchable<Doctor> {
    private final DataStore<Doctor> doctorStore = new DataStore<>();

    public void addDoctor(Doctor doctor) {
        doctorStore.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorStore.findAll();
    }

    public Doctor getDoctorById(String id) {
        return doctorStore.findById(id).orElse(null);
    }

    public void deleteDoctor(String id) {
        doctorStore.delete(id);
    }

    @Override
    public List<Doctor> search(String query) {
        return doctorStore.findAll().stream()
                .filter(d -> matchesIgnoreCase(d.getName(), query))
                .collect(Collectors.toList());
    }

    public List<Doctor> filterBySpecialization(Specialization specialization) {
        return doctorStore.findAll().stream()
                .filter(d -> d.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    public double averageConsultationFee() {
        return doctorStore.findAll().stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0);
    }

    public Map<String, Long> appointmentsPerDoctor(List<com.airtribe.meditrack.entity.Appointment> appointments) {
        return appointments.stream()
                .collect(Collectors.groupingBy(a -> a.getDoctor().getName(), Collectors.counting()));
    }
}

