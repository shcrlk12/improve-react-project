package com.unison.monitoring.common.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TimeChart {
    private final LocalDateTime timestamp;
    private final String windSpeed;
    private final String activePower;
    private final String rotorSpeed;
}
