package com.unison.monitoring.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Data")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class DataEntity implements Persistable<DataEntity.Id> {
    @EmbeddedId
    private Id id;

    @Column
    private String fullPerformance;

    @Column
    private String partialPerformance;

    @Column
    private String outOfElectrical;

    @Column
    private String outOfEnvironment;

    @Column
    private String requestedShutdown;

    @Column
    private String scheduledMaintenance;

    @Column
    private String technicalStandby;

    @Column
    private String rotorSpeed;

    @Column
    private String windSpeed;

    @Column
    private String nacOutTmp;

    @Column
    private String activePower;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean isNew() {
        return this.getCreatedAt() == null;
    }

    @Getter
    @Setter
    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {
        @Column
        private LocalDateTime timestamp;

        @ManyToOne
        @JoinColumn(name = "general_overview_uuid")  // 외래 키를 명시적으로 정의
        private GeneralOverviewEntity generalOverview;
    }
}
