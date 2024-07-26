package com.unison.monitoring.api.data;

import com.unison.monitoring.api.generalOverview.GeneralOverviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "Data")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class DataEntity {
    @EmbeddedId
    private Id id;

    @Column(nullable = true)
    private Double windSpeed;

    @Column(nullable = true)
    private Double rotorSpeed;

    @Column(nullable = true)
    private Double activePower;

    @Getter
    @Embeddable
    private static class Id implements Serializable {
        @Column
        private LocalDateTime timestamp;

        @ManyToOne
        @JoinColumn(name = "general_overview_uuid")  // 외래 키를 명시적으로 정의
        private GeneralOverviewEntity generalOverview;
    }
}
