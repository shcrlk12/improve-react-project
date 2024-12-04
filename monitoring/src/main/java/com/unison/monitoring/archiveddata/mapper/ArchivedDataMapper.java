package com.unison.monitoring.archiveddata.mapper;

import com.unison.monitoring.archiveddata.dto.ArchivedDataDto;
import com.unison.monitoring.archiveddata.entity.ArchivedDataEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ArchivedDataMapper {

    public static List<ArchivedDataDto> toDtoList(List<ArchivedDataEntity> archivedDataEntities){

        return archivedDataEntities.stream()
                .map(ArchivedDataMapper::toDto)
                .collect(Collectors.toList());
    }
    public static ArchivedDataDto toDto(ArchivedDataEntity archivedDataEntity){

        return ArchivedDataDto.builder()
                .fullPerformance(archivedDataEntity.getFullPerformance())
                .partialPerformance(archivedDataEntity.getPartialPerformance())
                .outOfElectrical(archivedDataEntity.getOutOfElectrical())
                .outOfEnvironment(archivedDataEntity.getOutOfEnvironment())
                .requestedShutdown(archivedDataEntity.getRequestedShutdown())
                .scheduledMaintenance(archivedDataEntity.getScheduledMaintenance())
                .technicalStandby(archivedDataEntity.getTechnicalStandby())
                .activePower(archivedDataEntity.getActivePower())
                .createdAt(archivedDataEntity.getCreatedAt())
                .createdBy(archivedDataEntity.getCreatedBy())
                .build();
    }
}
