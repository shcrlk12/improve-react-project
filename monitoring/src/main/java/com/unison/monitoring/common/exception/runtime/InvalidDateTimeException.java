package com.unison.monitoring.common.exception.runtime;

import java.time.LocalDateTime;

public class InvalidDateTimeException  extends CustomException{

    public InvalidDateTimeException(LocalDateTime time) {
        super("Invalid date time. " + time.toString());
    }
}
