package com.unison.monitoring.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTimeUtils {

    public static String formatLocalDateTime(String format, LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime parseLocalDateTime(String format, String formattedTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(formattedTime, formatter);
    }

    public static LocalDateTime parseLocalDateTime(String formattedTime) {
        List<String> formatPatterns = List.of(
                "yyyy-MM-dd HH:mm",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss.S",
                "yyyy-MM-dd HH:mm:ss.SS",
                "yyyy-MM-dd HH:mm:ss.SSS"
        );

        for (String pattern : formatPatterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(formattedTime, formatter);
            } catch (DateTimeParseException e) {
            }
        }

        return null;
    }

    public static LocalDateTime parseISOLocalDateTime(String formattedTime) {
        List<String> formatPatterns = List.of(
                "yyyy-MM-dd'T'HH:mm",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss.S",
                "yyyy-MM-dd'T'HH:mm:ss.SS",
                "yyyy-MM-dd'T'HH:mm:ss.SSS"
        );

        for (String pattern : formatPatterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(formattedTime, formatter);
            } catch (DateTimeParseException e) {
            }
        }

        return null;
    }

    public static LocalDateTime formatAndParseLocalDateTime(String format, LocalDateTime localDateTime){
        String formattedTime = formatLocalDateTime(format, localDateTime);

        return parseLocalDateTime("yyyy-MM-dd HH:mm:ss", formattedTime);
    }
}
