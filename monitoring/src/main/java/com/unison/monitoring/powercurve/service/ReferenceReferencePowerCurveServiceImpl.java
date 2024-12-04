package com.unison.monitoring.powercurve.service;


import com.unison.monitoring.powercurve.dto.PowerCurveDto;
import com.unison.monitoring.powercurve.entity.PowerCurveEntity;
import com.unison.monitoring.powercurve.mapper.ReferencePowerCurveMapper;
import com.unison.monitoring.powercurve.repository.PowerCurveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferenceReferencePowerCurveServiceImpl implements ReferencePowerCurveService {
    private final PowerCurveRepository powerCurveRepository;
    @Override
    public List<PowerCurveDto> getReferencePowerCurve(UUID uuid) {
        List<PowerCurveEntity> powerCurveEntities = powerCurveRepository.findByGeneralOverviewEntityUuid(uuid);

        return ReferencePowerCurveMapper.toDto(powerCurveEntities);
    }
}
