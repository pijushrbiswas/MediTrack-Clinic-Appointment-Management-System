package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.stream.Collectors;

public class PatientService implements Searchable<Patient> {
    private final DataStore<Patient> patientStore = new DataStore<>();

    public void addPatient(Patient patient) {
        patientStore.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientStore.findAll();
    }

    public Patient searchPatient(String id) {
        return patientStore.findById(id).orElse(null);
    }

    public List<Patient> searchPatient(String name, boolean byName) {
        if (!byName) {
            return List.of();
        }
        return patientStore.findAll().stream()
                .filter(p -> matchesIgnoreCase(p.getName(), name))
                .collect(Collectors.toList());
    }

    public List<Patient> searchPatient(int age) {
        return patientStore.findAll().stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public List<Patient> search(String query) {
        return searchPatient(query, true);
    }
}

