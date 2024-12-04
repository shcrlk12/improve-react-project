package com.unison.monitoring.data.dto;

import com.unison.monitoring.data.entity.DataEntity;
import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class DataDto {
    private UUID turbineUuid;
    private LocalDateTime timestamp;
    private Integer fullPerformance;
    private Integer partialPerformance;
    private Integer outOfElectrical;
    private Integer outOfEnvironment;
    private Integer requestedShutdown;
    private Integer scheduledMaintenance;
    private Integer technicalStandby;
    private Double rotorSpeed;
    private Double windSpeed;
    private Double nacOutTmp;
    private Double activePower;
    private LocalDateTime createdAt;
}
