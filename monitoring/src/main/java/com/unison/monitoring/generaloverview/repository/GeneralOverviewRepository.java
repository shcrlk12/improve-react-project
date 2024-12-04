package com.unison.monitoring.generaloverview.repository;

import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeneralOverviewRepository extends JpaRepository<GeneralOverviewEntity, UUID> {

    Optional<GeneralOverviewEntity> findByUuidAndIsActiveTrue(UUID uuid);

    List<GeneralOverviewEntity> findByIsActiveTrue();
}
