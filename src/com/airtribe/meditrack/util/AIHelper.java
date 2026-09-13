package com.airtribe.meditrack.util;

import com.airtribe.meditrack.enums.Specialization;

import java.util.Locale;

public final class AIHelper {
    private AIHelper() {
    }

    public static Specialization recommendSpecialization(String symptoms) {
        String s = symptoms == null ? "" : symptoms.toLowerCase(Locale.ROOT);
        if (s.contains("skin") || s.contains("rash")) return Specialization.DERMATOLOGIST;
        if (s.contains("heart") || s.contains("chest")) return Specialization.CARDIOLOGIST;
        if (s.contains("headache") || s.contains("neuro")) return Specialization.NEUROLOGIST;
        if (s.contains("bone") || s.contains("joint")) return Specialization.ORTHOPEDIC;
        if (s.contains("child") || s.contains("baby")) return Specialization.PEDIATRICIAN;
        return Specialization.GENERAL_PHYSICIAN;
    }
}
