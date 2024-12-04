package com.unison.monitoring.common.exception.runtime;

public class LocalDateTimeNullPointException extends CustomException{

    public LocalDateTimeNullPointException(String message) {
        super("[" + message + "] local date tim variable null point exception.");
    }
}
