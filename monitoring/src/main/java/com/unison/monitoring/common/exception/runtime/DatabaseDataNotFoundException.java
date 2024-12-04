package com.unison.monitoring.common.exception.runtime;

public class DatabaseDataNotFoundException extends CustomException{

    public DatabaseDataNotFoundException(String message) {
        super("[" + message + "] data not found in database.");
    }
}
