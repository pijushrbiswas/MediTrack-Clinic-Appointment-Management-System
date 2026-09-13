package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.observer.AppointmentObserver;
import com.airtribe.meditrack.observer.AppointmentSubject;
import com.airtribe.meditrack.util.DataStore;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class AppointmentService implements AppointmentSubject {
    private final DataStore<Appointment> appointmentStore = new DataStore<>();
    private final List<AppointmentObserver> observers = new ArrayList<>();
    private final Timer reminderTimer = new Timer(true);
    private final AtomicInteger reminderCount = new AtomicInteger(0);

    public Appointment createAppointment(Appointment appointment) {
        appointmentStore.save(appointment);
        notifyObservers(appointment, "APPOINTMENT_CREATED");
        scheduleReminder(appointment);
        return appointment;
    }

    public Appointment createAppointment(String id, Patient patient, Doctor doctor, LocalDateTime dateTime) {
        Appointment appointment = new Appointment(id, patient, doctor, dateTime, AppointmentStatus.CONFIRMED);
        return createAppointment(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentStore.findAll();
    }

    public Appointment cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentStore.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + appointmentId));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        notifyObservers(appointment, "APPOINTMENT_CANCELLED");
        return appointment;
    }

    @Override
    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(AppointmentObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Appointment appointment, String eventType) {
        for (AppointmentObserver observer : observers) {
            observer.update(appointment, eventType);
        }
    }

    private void scheduleReminder(Appointment appointment) {
        long delay = Duration.between(LocalDateTime.now(), appointment.getAppointmentDateTime().minusMinutes(30)).toMillis();
        if (delay <= 0) {
            return;
        }
        reminderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Reminder: Appointment in 30 minutes -> " + appointment);
                reminderCount.incrementAndGet();
            }
        }, delay);
    }

    public int getReminderCount() {
        return reminderCount.get();
    }
}
