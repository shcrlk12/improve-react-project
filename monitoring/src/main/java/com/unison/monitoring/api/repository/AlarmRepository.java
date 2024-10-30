package com.unison.monitoring.api.repository;

import com.unison.monitoring.api.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {
}
