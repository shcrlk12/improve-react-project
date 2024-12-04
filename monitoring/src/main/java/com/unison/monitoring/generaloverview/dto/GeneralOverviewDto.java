package com.unison.monitoring.generaloverview.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
public class GeneralOverviewDto {
    private UUID turbineUuid;
    private String siteName;
    private Integer ratedPower;
    private String description;
    private String remark;
    private Integer altitude;
    private Integer hubHeight;
    private LocalDateTime commissionDate;
    private LocalDateTime lastDataSyncDate;
    private LocalDateTime lastAlarmSyncDate;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
