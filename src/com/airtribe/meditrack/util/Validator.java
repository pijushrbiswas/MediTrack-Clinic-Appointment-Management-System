package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

public final class Validator {
    private Validator() {
    }

    public static String requireNonBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidDataException(message);
        }
        return value.trim();
    }

    public static String requirePhone(String phone) {
        String p = requireNonBlank(phone, "Phone cannot be blank");
        if (!p.matches("\\d{10}")) {
            throw new InvalidDataException("Phone must be exactly 10 digits");
        }
        return p;
    }

    public static void requirePositive(double value, String message) {
        if (value <= 0) {
            throw new InvalidDataException(message);
        }
    }

    public static void requireRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new InvalidDataException(message);
        }
    }
}

