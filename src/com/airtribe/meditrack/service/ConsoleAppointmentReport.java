package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;

import java.util.List;

public class ConsoleAppointmentReport extends AppointmentReportTemplate {
    @Override
    protected void printHeader() {
        System.out.println("==== Appointment Report ====");
    }

    @Override
    protected void printBody(List<Appointment> appointments) {
        appointments.forEach(System.out::println);
    }
}
