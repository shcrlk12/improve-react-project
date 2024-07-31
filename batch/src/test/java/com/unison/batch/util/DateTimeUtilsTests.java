package com.unison.batch.util;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeUtilsTests {

    @Test
    public void testFormatLocalDateTime(){
        //Given
        String expectedDate = "2024-11-05 04:12:03";
        LocalDateTime formattingDate = LocalDateTime.of(2024, 11, 5, 4, 12, 3);

        //When
        String actualData = DateTimeUtils.formatLocalDateTime("yyyy-MM-dd HH:mm:ss", formattingDate);

        //Then
        assertEquals(actualData, expectedDate);
    }

    @Test
    public void testParseLocalDateTime() {
        // Given
        LocalDateTime expectedDate = LocalDateTime.of(2024, 11, 5, 4, 12, 3);
        String formattingDate = "2024-11-05 04:12:03";

        // When
        LocalDateTime actualDate = DateTimeUtils.parseLocalDateTime("yyyy-MM-dd HH:mm:ss", formattingDate);

        // Then
        assertEquals(expectedDate.getYear(), actualDate.getYear(), "Year should match");
        assertEquals(expectedDate.getMonth().getValue(), actualDate.getMonth().getValue(), "Month should match");
        assertEquals(expectedDate.getDayOfMonth(), actualDate.getDayOfMonth(), "Day of Month should match");
        assertEquals(expectedDate.getHour(), actualDate.getHour(), "Hour should match");
        assertEquals(expectedDate.getMinute(), actualDate.getMinute(), "Minute should match");
        assertEquals(expectedDate.getSecond(), actualDate.getSecond(), "Second should match");
    }

    @Test
    public void testFormatAndParseLocalDateTime(){
        // Given
        LocalDateTime expectedDate = LocalDateTime.of(2024, 11, 5, 0, 0, 0);
        LocalDateTime formattingDate = LocalDateTime.of(2024, 11, 5, 4, 12, 3);

        // When
        LocalDateTime actualDate = DateTimeUtils.formatAndParseLocalDateTime("yyyy-MM-dd 00:00:00", formattingDate);

        // Then
        assertEquals(expectedDate.getYear(), actualDate.getYear(), "Year should match");
        assertEquals(expectedDate.getMonth().getValue(), actualDate.getMonth().getValue(), "Month should match");
        assertEquals(expectedDate.getDayOfMonth(), actualDate.getDayOfMonth(), "Day of Month should match");
        assertEquals(expectedDate.getHour(), actualDate.getHour(), "Hour should match");
        assertEquals(expectedDate.getMinute(), actualDate.getMinute(), "Minute should match");
        assertEquals(expectedDate.getSecond(), actualDate.getSecond(), "Second should match");
    }
}
