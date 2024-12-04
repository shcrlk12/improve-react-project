package com.unison.monitoring.powercurve.entity;

import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "PowerCurve")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class PowerCurveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "general_overview_uuid")
    private GeneralOverviewEntity generalOverviewEntity;

    @Column(nullable = false)
    private Double windSpeed;

    @Column(nullable = false)
    private Double activePower;
}
