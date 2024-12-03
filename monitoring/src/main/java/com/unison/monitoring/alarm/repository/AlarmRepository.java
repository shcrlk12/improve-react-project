package com.unison.monitoring.api.repository;

import com.unison.monitoring.api.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    @Query("SELECT a " +
            "FROM AlarmEntity a " +
                "WHERE a.id.turbineUuid = :uuid " +
                    "and a.id.timestamp >= :startTimestamp " +
                    "and a.id.timestamp < :endTimestamp")
    List<AlarmEntity> findByTurbineUuidAndTimestamp(@Param("uuid") UUID uuid, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

}
