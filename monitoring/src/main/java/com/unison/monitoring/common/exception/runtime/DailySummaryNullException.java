package com.unison.monitoring.common.exception.runtime;

public class DailySummaryNullException extends CustomException{

    public DailySummaryNullException(String message) {
        super("[" + message + "] cannot found daily summary information.");
    }
}
