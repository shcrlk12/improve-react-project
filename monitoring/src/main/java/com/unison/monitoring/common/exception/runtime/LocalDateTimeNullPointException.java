package com.unison.monitoring.common.exception;

public class LocalDateTimeNullPointException extends RuntimeException{

    public LocalDateTimeNullPointException(String message) {
        super("[" + message + "] local date tim variable null point exception.");
    }
}
