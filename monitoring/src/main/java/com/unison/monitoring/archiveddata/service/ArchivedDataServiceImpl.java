package com.unison.monitoring.archiveddata.service;

import com.unison.monitoring.archiveddata.dto.ArchivedDataDto;
import com.unison.monitoring.archiveddata.entity.ArchivedDataEntity;
import com.unison.monitoring.archiveddata.mapper.ArchivedDataMapper;
import com.unison.monitoring.archiveddata.repository.ArchivedDataRepository;
import com.unison.monitoring.common.exception.runtime.TurbineUuidNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArchivedDataServiceImpl implements ArchivedDataService{
    private final ArchivedDataRepository archivedDataRepository;

    @Override
    public List<ArchivedDataDto> getAllArchivedData() {

        List<ArchivedDataEntity> archivedDataEntity = archivedDataRepository.findAll();

        return ArchivedDataMapper.toDtoList(archivedDataEntity);
    }

    @Override
    public ArchivedDataDto getArchivedDataByUUID(UUID turbineUuid) {

        ArchivedDataEntity archivedDataEntity = archivedDataRepository.findById(turbineUuid)
                .orElseThrow(() -> new TurbineUuidNotFoundException("archived data ",turbineUuid));

        return ArchivedDataMapper.toDto(archivedDataEntity);
    }
}
