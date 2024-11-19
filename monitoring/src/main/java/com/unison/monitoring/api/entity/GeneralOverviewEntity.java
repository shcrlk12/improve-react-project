package com.unison.monitoring.api.entity;

import com.unison.monitoring.security.UserDetailImpl;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "GeneralOverview")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class GeneralOverviewEntity implements Persistable<UUID> {
    @Id
    private UUID uuid;

    @Column(nullable = false)
    private String siteName;

    @Column(nullable = false)
    private Integer ratedPower;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String remark;

    @Column(nullable = false)
    private Integer altitude;

    @Column(nullable = false)
    private Integer hubHeight;

    @Column(nullable = false)
    private LocalDateTime commissionDate;

    @Column(nullable = false)
    @Setter
    private LocalDateTime lastDataSyncDate;

    @Column(nullable = false)
    @Setter
    private LocalDateTime lastAlarmSyncDate;

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

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String updatedBy;
    public GeneralOverviewEntity(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public boolean isNew() {
        return updatedAt == null;
    }

    public void setLastAlarmSyncDate(LocalDateTime date){
        this.lastAlarmSyncDate = date;
        this.updatedAt = LocalDateTime.now();
    }

    public void setLastDataSyncDate(LocalDateTime date){
        this.lastDataSyncDate = date;
        this.updatedAt = LocalDateTime.now();
    }
    public void updateRemark(String remark, UserDetailImpl userDetail){
        this.remark = remark;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = userDetail.getUsername();
    }
}
