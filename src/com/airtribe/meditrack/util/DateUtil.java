package com.airtribe.meditrack.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateUtil() {
    }

    public static LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, FORMATTER);
    }

    public static String format(LocalDateTime value) {
        return value.format(FORMATTER);
    }
}

