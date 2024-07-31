package com.unison.batch.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static String formatLocalDateTime(String format, LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime parseLocalDateTime(String format, String formattedTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(formattedTime, formatter);
    }

    public static LocalDateTime formatAndParseLocalDateTime(String format, LocalDateTime localDateTime){
        String formattedTime = formatLocalDateTime(format, localDateTime);

        return parseLocalDateTime("yyyy-MM-dd HH:mm:ss", formattedTime);
    }
}
