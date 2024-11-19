package com.unison.monitoring.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyDataStats {
    private Double averageWindSpeed;
    private Double totalActivePower;
    private Long totalFullPerformance;
    private Long totalPartialPerformance;
    private Long totalOutOfElectrical;
    private Long totalOutOfEnvironment;
    private Long totalRequestedShutdown;
    private Long totalScheduledMaintenance;
    private Long totalTechnicalStandby;

    public Long getOperatingTime(){
        return totalFullPerformance
                + totalPartialPerformance
                + totalOutOfElectrical
                + totalOutOfEnvironment
                + totalRequestedShutdown
                + totalScheduledMaintenance
                + totalTechnicalStandby;
    }

    public Long getGeneratingTime(){
        return totalFullPerformance
                + totalPartialPerformance;
    }
}