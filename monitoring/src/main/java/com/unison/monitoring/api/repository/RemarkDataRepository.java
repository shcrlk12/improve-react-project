package com.unison.monitoring.api.repository;

import com.unison.monitoring.api.entity.RemarkDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RemarkDataRepository extends JpaRepository<RemarkDataEntity, UUID> {
    @Query("SELECT r " +
            "FROM RemarkDataEntity r " +
            "WHERE r.generalOverviewEntity.uuid = :uuid " +
            "and r.timestamp >= :startTimestamp " +
            "and r.timestamp < :endTimestamp " +
            "and r.remarkMetaEntity.id IN (:remarkIds)")
    List<RemarkDataEntity> findByUuidAndTimestamp(@Param("uuid") UUID uuid, @Param("remarkIds") List<Integer> remarkIds, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @Query("SELECT r " +
            "FROM RemarkDataEntity r " +
            "WHERE r.generalOverviewEntity.uuid = :uuid " +
            "and r.timestamp >= :startTimestamp " +
            "and r.timestamp < :endTimestamp " +
            "and r.remarkMetaEntity.id IN (:remarkIds) " +
            "Order by r.remarkMetaEntity.id asc")
    List<RemarkDataEntity> findByUuidAndTimestampOrderById(@Param("uuid") UUID uuid, @Param("remarkIds") List<Integer> remarkIds, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    List<RemarkDataEntity> findByUuidIn(List<UUID> uuids);
}
