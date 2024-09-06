package com.unison.monitoring.api.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeneralOverviewRepository extends JpaRepository<GeneralOverviewEntity, UUID> {
}
