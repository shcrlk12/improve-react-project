package com.unison.monitoring.report.service;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DailyReportSummary {
    private LocalDateTime commissionDate;
    private LocalDateTime writtenDate;
    private String operatingPeriod;
    private Double windSpeed;
    private Double activePower;
    private Integer operatingTime;
    private Integer generatingTime;
    private Double totalActivePower;
    private Integer totalOperatingTime;
    private Integer totalGeneratingTime;
    private Integer ratedPower;
}
