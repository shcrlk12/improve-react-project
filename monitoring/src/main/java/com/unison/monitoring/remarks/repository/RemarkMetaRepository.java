package com.unison.monitoring.remarks.repository;

import com.unison.monitoring.remarks.entity.RemarkMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RemarkMetaRepository extends JpaRepository<RemarkMetaEntity, Integer> {

    List<RemarkMetaEntity> findByGeneralOverviewEntityUuidOrderByOrderId(UUID uuid);
}
