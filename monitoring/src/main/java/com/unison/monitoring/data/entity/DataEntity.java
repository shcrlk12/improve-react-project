package com.unison.monitoring.data.entity;

import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
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
    private Integer fullPerformance;

    @Column
    private Integer partialPerformance;

    @Column
    private Integer outOfElectrical;

    @Column
    private Integer outOfEnvironment;

    @Column
    private Integer requestedShutdown;

    @Column
    private Integer scheduledMaintenance;

    @Column
    private Integer technicalStandby;

    @Column
    private Double rotorSpeed;

    @Column
    private Double windSpeed;

    @Column
    private Double nacOutTmp;

    @Column
    private Double activePower;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @Getter
    @Setter
    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {
        @Column
        private LocalDateTime timestamp;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "general_overview_uuid")
        private GeneralOverviewEntity generalOverviewEntity;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean isNew() {
        return this.getCreatedAt() == null;
    }
}
