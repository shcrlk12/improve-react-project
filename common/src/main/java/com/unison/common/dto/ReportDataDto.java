package com.unison.common.dto;


import lombok.*;

@Getter
@RequiredArgsConstructor
public class ReportDataDto {
    public final static String TYPE = "10min-data";

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String fullPerformance;
        private String partialPerformance;
        private String outOfElectrical;
        private String outOfEnvironment;
        private String requestedShutdown;
        private String scheduledMaintenance;
        private String technicalStandby;
        private String rotorSpeed;
        private String windSpeed;
        private String nacOutTmp;
        private String activePower;
    }
}
