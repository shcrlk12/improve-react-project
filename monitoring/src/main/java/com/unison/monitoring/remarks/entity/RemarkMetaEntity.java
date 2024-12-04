package com.unison.monitoring.remarks.entity;

import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "RemarkMeta")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class RemarkMetaEntity {
    @Id
    UUID uuid;

    @Column(nullable = false)
    Integer orderId;

    @Column(nullable = false)
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_overview_uuid", nullable = false)
    GeneralOverviewEntity generalOverviewEntity;

    @Column(nullable = false)
    String defaultDescription;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @ColumnDefault("'System'")
    private String createdBy;

    @UpdateTimestamp
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String updatedBy;
}
