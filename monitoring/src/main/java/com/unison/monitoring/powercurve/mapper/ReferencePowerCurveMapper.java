package com.unison.monitoring.powercurve.mapper;

import com.unison.monitoring.powercurve.dto.PowerCurveDto;
import com.unison.monitoring.powercurve.entity.PowerCurveEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ReferencePowerCurveMapper {
    public static List<PowerCurveDto> toDto(List<PowerCurveEntity> powerCurveEntities){

        return powerCurveEntities.stream()
                .map(ReferencePowerCurveMapper::toDto)
                .collect(Collectors.toList());
    }

    public static PowerCurveDto toDto(PowerCurveEntity powerCurveEntity){

        return new PowerCurveDto(
                powerCurveEntity.getGeneralOverviewEntity().getUuid(),
                String.valueOf(powerCurveEntity.getWindSpeed()),
                String.valueOf(powerCurveEntity.getActivePower())
        );
    }
}
