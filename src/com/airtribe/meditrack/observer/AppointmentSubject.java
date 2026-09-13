package com.airtribe.meditrack.observer;

public interface AppointmentSubject {
    void addObserver(AppointmentObserver observer);

    void removeObserver(AppointmentObserver observer);
}

