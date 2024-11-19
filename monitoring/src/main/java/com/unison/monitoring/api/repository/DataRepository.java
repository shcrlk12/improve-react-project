package com.unison.monitoring.api.repository;

import com.unison.monitoring.api.DailyDataStats;
import com.unison.monitoring.api.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataRepository extends JpaRepository<DataEntity, DataEntity.Id> {

    @Query("SELECT d " +
            "FROM DataEntity d " +
                "WHERE d.id.generalOverviewEntity.uuid = :uuid " +
                    "and d.id.timestamp >= :startTimestamp " +
                    "and d.id.timestamp < :endTimestamp")
    List<DataEntity> findByUuidAndTimestamp(@Param("uuid") UUID uuid, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @Query("SELECT new com.unison.monitoring.api.DailyDataStats(AVG(d.windSpeed), SUM(d.activePower), SUM(d.fullPerformance), SUM(d.partialPerformance), SUM(d.outOfElectrical), SUM(d.outOfEnvironment), SUM(d.requestedShutdown), SUM(d.scheduledMaintenance), SUM(d.technicalStandby)) " +
            "FROM DataEntity d " +
            "WHERE d.id.generalOverviewEntity.uuid = :uuid " +
            "AND d.id.timestamp >= :startTimestamp " +
            "AND d.id.timestamp < :endTimestamp")
    Optional<DailyDataStats> findDailyDataByUuidAnd(@Param("uuid") UUID uuid,
                                                   @Param("startTimestamp") LocalDateTime startTimestamp,
                                                   @Param("endTimestamp") LocalDateTime endTimestamp);

}