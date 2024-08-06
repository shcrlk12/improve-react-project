package com.unison.monitoring.api.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportDataDto {
    public final static String TYPE = "data";

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class Request{
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
}
