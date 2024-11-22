package com.unison.monitoring.api.repository;

import com.unison.monitoring.api.entity.ArchivedDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArchivedDataRepository extends JpaRepository<ArchivedDataEntity, UUID> {
}
