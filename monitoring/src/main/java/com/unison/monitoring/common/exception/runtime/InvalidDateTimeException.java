package com.unison.monitoring.common.exception;

import java.time.LocalDateTime;

public class InvalidDateTimeException  extends RuntimeException{

    public InvalidDateTimeException(LocalDateTime time) {
        super("Invalid date time. " + time.toString());
    }
}
