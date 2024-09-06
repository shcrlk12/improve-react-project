package com.unison.monitoring.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class ReportData {
    private final UUID uuid;
    private final String measuredDate;
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
