package com.unison.monitoring.generaloverview.service;

import com.unison.monitoring.common.exception.runtime.TurbineUuidNotFoundException;
import com.unison.monitoring.generaloverview.dto.GeneralOverviewDto;
import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import com.unison.monitoring.generaloverview.mapper.GeneralOverviewMapper;
import com.unison.monitoring.generaloverview.repository.GeneralOverviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneralOverviewServiceImpl implements GeneralOverviewService{
    private final GeneralOverviewRepository generalOverviewRepository;
    @Override
    public GeneralOverviewDto findByTurbineUuid(UUID turbineUuid) {
        GeneralOverviewEntity generalOverviewEntity = generalOverviewRepository.findByUuidAndIsActiveTrue(turbineUuid)
                .orElseThrow(() -> new TurbineUuidNotFoundException("general overview ", turbineUuid));

        return GeneralOverviewMapper.toDto(generalOverviewEntity);
    }

    @Override
    public List<GeneralOverviewDto> findAll() {
        List<GeneralOverviewEntity> generalOverviewEntities = generalOverviewRepository.findByIsActiveTrue();

        return GeneralOverviewMapper.toDtoList(generalOverviewEntities);
    }


}
