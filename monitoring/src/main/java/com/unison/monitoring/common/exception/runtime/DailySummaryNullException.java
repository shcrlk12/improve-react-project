package com.unison.monitoring.common.exception;

public class DailySummaryNullException extends RuntimeException{

    public DailySummaryNullException(String message) {
        super("[" + message + "] cannot found daily summary information.");
    }
}
