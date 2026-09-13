package com.airtribe.meditrack.constants;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
    public static final double TAX_RATE;
    public static final String PATIENT_CSV_PATH;
    public static final String DOCTOR_CSV_PATH;
    public static final String APPOINTMENT_CSV_PATH;
    public static final Map<String, String> APP_CONFIG = new HashMap<>();

    static {
        TAX_RATE = 0.10;
        PATIENT_CSV_PATH = "data/patients.csv";
        DOCTOR_CSV_PATH = "data/doctors.csv";
        APPOINTMENT_CSV_PATH = "data/appointments.csv";
        APP_CONFIG.put("appName", "MediTrack");
        APP_CONFIG.put("version", "1.0.0");
    }

    private Constants() {
    }
}
