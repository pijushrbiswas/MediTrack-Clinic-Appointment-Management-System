package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Doctor;

import java.util.Comparator;

public final class DoctorComparators {
    public static final Comparator<Doctor> BY_NAME = Comparator.comparing(Doctor::getName);
    public static final Comparator<Doctor> BY_FEE = Comparator.comparingDouble(Doctor::getConsultationFee);

    private DoctorComparators() {
    }
}
