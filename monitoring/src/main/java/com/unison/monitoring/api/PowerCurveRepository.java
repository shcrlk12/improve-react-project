package com.unison.monitoring.api;

import com.unison.monitoring.api.entity.PowerCurveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PowerCurveRepository extends JpaRepository<PowerCurveEntity, Long> {
    @Query("SELECT p FROM PowerCurveEntity p WHERE p.generalOverviewEntity.uuid = :uuid")
    List<PowerCurveEntity> findByGeneralOverviewEntityUuid(@Param("uuid") UUID uuid);

}

