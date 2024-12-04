package com.unison.monitoring.archiveddata.repository;

import com.unison.monitoring.archiveddata.entity.ArchivedDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArchivedDataRepository extends JpaRepository<ArchivedDataEntity, UUID> {
}
