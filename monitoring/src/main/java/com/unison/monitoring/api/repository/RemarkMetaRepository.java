package com.unison.monitoring.api.repository;

import com.unison.monitoring.api.entity.RemarkMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RemarkMetaRepository extends JpaRepository<RemarkMetaEntity, Integer> {

    List<RemarkMetaEntity> findByGeneralOverviewEntityUuidOrderById(UUID uuid);
}
