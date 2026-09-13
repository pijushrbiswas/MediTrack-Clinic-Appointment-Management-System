package com.airtribe.meditrack.observer;

import com.airtribe.meditrack.entity.Appointment;

public class ConsoleNotificationObserver implements AppointmentObserver {
    @Override
    public void update(Appointment appointment, String eventType) {
        System.out.println("[Notification] " + eventType + " -> " + appointment);
    }
}
