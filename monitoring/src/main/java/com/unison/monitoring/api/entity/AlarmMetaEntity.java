package com.unison.monitoring.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "AlarmMeta")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class AlarmMetaEntity {

    @EmbeddedId
    private Id id;

    @Column
    private String alarmName;

    @MapsId(value = "uuid")
    @OneToOne(targetEntity = GeneralOverviewEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "general_overview_uuid")
    private GeneralOverviewEntity generalOverviewEntity;

    @Getter
    @Setter
    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {
        @Column
        private UUID uuid;

        @Column
        private String alarmNumber;
    }
}
