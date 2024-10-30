package com.unison.monitoring.api.repository;

import com.unison.monitoring.api.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<DataEntity, DataEntity.Id> {
}
