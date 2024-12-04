package com.unison.monitoring.archiveddata.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ArchivedDataDto {
    private Integer fullPerformance;
    private Integer partialPerformance;
    private Integer outOfElectrical;
    private Integer outOfEnvironment;
    private Integer requestedShutdown;
    private Integer scheduledMaintenance;
    private Integer technicalStandby;
    private Double activePower;
    private LocalDateTime createdAt;
    private String createdBy;
}
