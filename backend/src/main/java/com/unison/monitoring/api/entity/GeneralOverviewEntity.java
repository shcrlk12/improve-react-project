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
import java.util.UUID;

@Entity
@Getter
@Table(name = "GeneralOverview")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class GeneralOverviewEntity {
    @Id
    private UUID uuid;

    @Column(nullable = false)
    private String siteName;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean isActive;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isDelete;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @ColumnDefault("'System'")
    private String createdBy;
}
