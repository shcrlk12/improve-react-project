package com.unison.monitoring.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "UpdateRecord")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class UpdateRecordEntity {

    @Id
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_overview_uuid")
    private GeneralOverviewEntity generalOverviewEntity;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedDate;
}
