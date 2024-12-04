package com.unison.monitoring.data.repository;

import com.unison.monitoring.data.entity.DataEntity;
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


    @Query(value = "SELECT SUM(dailyPower) as totalPower " +
            "FROM (" +
            "    SELECT CONVERT(CHAR(8), timestamp, 2) AS date," +
            "           AVG(active_power) AS dailyPower" +
            "    FROM [Monitoring].[dbo].[data] d " +
            "    WHERE d.general_overview_uuid = :uuid " +
            "       AND d.timestamp >= :startTimestamp " +
            "       AND d.timestamp < :endTimestamp " +
            "    GROUP BY CONVERT(CHAR(8), timestamp, 2)" +
            ") AS subquery",
            nativeQuery = true)
    Optional<Double> findTotalActivePowerLikeScada(@Param("uuid") UUID uuid,
                                                    @Param("startTimestamp") LocalDateTime startTimestamp,
                                                    @Param("endTimestamp") LocalDateTime endTimestamp);



}
