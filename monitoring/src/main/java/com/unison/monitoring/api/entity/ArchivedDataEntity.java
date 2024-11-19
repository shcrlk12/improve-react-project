package com.unison.monitoring.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "ArchivedData")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class ArchivedDataEntity {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_overview_uuid")
    private GeneralOverviewEntity generalOverviewEntity;

    @Column(nullable = false)
    private Integer fullPerformance;

    @Column(nullable = false)
    private Integer partialPerformance;

    @Column(nullable = false)
    private Integer outOfElectrical;

    @Column(nullable = false)
    private Integer outOfEnvironment;

    @Column(nullable = false)
    private Integer requestedShutdown;

    @Column(nullable = false)
    private Integer scheduledMaintenance;

    @Column(nullable = false)
    private Integer technicalStandby;

    @Column(nullable = false)
    private Double activePower;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @ColumnDefault("'System'")
    private String createdBy;

}