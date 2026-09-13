package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;

import java.util.List;

public abstract class AppointmentReportTemplate {
    public final void generate(List<Appointment> appointments) {
        printHeader();
        printBody(appointments);
        printFooter(appointments);
    }

    protected abstract void printHeader();

    protected abstract void printBody(List<Appointment> appointments);

    protected void printFooter(List<Appointment> appointments) {
        System.out.println("Total appointments: " + appointments.size());
    }
}

