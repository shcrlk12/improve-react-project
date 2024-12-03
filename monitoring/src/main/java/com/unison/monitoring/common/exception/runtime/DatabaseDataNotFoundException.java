package com.unison.monitoring.common.exception;

public class DatabaseDataNotFoundException extends RuntimeException{

    public DatabaseDataNotFoundException(String message) {
        super("[" + message + "] data not found in database.");
    }
}
