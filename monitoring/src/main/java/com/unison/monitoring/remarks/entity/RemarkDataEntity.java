package com.unison.monitoring.remarks.entity;

import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "RemarkData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Setter
public class RemarkDataEntity implements Persistable<UUID> {
    @Id
    UUID uuid;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_overview_uuid")
    private GeneralOverviewEntity generalOverviewEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remark_meta_uuid")
    private RemarkMetaEntity remarkMetaEntity;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String updatedBy;

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public boolean isNew() {
        return this.updatedAt == null;
    }
}
