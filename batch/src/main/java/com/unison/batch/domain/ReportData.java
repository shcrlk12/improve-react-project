package com.unison.batch.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


/**
 * 10Min data object
 */
@RequiredArgsConstructor
@Getter
public class ReportData {
    public static UUID uuid;

    private final String measureDate;
    private final String fullPerformance;
    private final String partialPerformance;
    private final String outOfElectrical;
    private final String outOfEnvironment;
    private final String requestedShutdown;
    private final String scheduledMaintenance;
    private final String technicalStandby;
    private final String rotorSpeed;
    private final String windSpeed;
    private final String nacOutTmp;
    private final String activePower;
}