package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Address;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.InvalidDataException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CSVUtil {
    private CSVUtil() {
    }

    public static void savePatients(String path, List<Patient> patients) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
            for (Patient p : patients) {
                String line = String.join(",",
                        p.getId(),
                        p.getName(),
                        p.getPhone(),
                        String.valueOf(p.getAge()),
                        p.getAddress() == null ? "" : p.getAddress().getLine1(),
                        p.getAddress() == null ? "" : p.getAddress().getCity());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Failed to save patients to CSV", e);
        }
    }

    public static List<Patient> loadPatients(String path) {
        List<Patient> patients = new ArrayList<>();
        if (!Files.exists(Path.of(path))) {
            return patients;
        }
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                Address address = new Address(parts[4], parts[5]);
                patients.add(new Patient(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), address));
            }
        } catch (IOException e) {
            throw new InvalidDataException("Failed to load patients from CSV", e);
        }
        return patients;
    }

    public static void saveDoctors(String path, List<Doctor> doctors) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
            for (Doctor d : doctors) {
                String line = String.join(",",
                        d.getId(),
                        d.getName(),
                        d.getPhone(),
                        d.getSpecialization().name(),
                        String.valueOf(d.getConsultationFee()));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Failed to save doctors to CSV", e);
        }
    }

    public static List<Doctor> loadDoctors(String path) {
        List<Doctor> doctors = new ArrayList<>();
        if (!Files.exists(Path.of(path))) {
            return doctors;
        }
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                doctors.add(new Doctor(parts[0], parts[1], parts[2], Specialization.valueOf(parts[3]), Double.parseDouble(parts[4])));
            }
        } catch (IOException e) {
            throw new InvalidDataException("Failed to load doctors from CSV", e);
        }
        return doctors;
    }

    public static void saveAppointments(String path, List<Appointment> appointments) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
            for (Appointment a : appointments) {
                String line = String.join(",",
                        a.getId(),
                        a.getPatient().getId(),
                        a.getDoctor().getId(),
                        DateUtil.format(a.getAppointmentDateTime()),
                        a.getStatus().name());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Failed to save appointments to CSV", e);
        }
    }

    public static List<Appointment> loadAppointments(String path, Map<String, Patient> patientById, Map<String, Doctor> doctorById) {
        List<Appointment> appointments = new ArrayList<>();
        if (!Files.exists(Path.of(path))) {
            return appointments;
        }
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                Patient patient = patientById.get(parts[1]);
                Doctor doctor = doctorById.get(parts[2]);
                if (patient != null && doctor != null) {
                    appointments.add(new Appointment(parts[0], patient, doctor, DateUtil.parse(parts[3]), AppointmentStatus.valueOf(parts[4])));
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Failed to load appointments from CSV", e);
        }
        return appointments;
    }
}
