package com.unison.monitoring.api.remark;

import com.unison.common.domain.Remark;
import com.unison.monitoring.api.DataMapper;
import com.unison.monitoring.api.entity.RemarkDataEntity;
import com.unison.monitoring.api.entity.RemarkMetaEntity;
import com.unison.monitoring.api.repository.RemarkDataRepository;
import com.unison.monitoring.api.repository.RemarkMetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemarkServiceImpl implements RemarkService{
    private final RemarkDataRepository remarkDataRepository;
    private final RemarkMetaRepository remarkMetaRepository;

    @Override
    public List<Remark> getRemarks(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public List<Remark> getRemarksInADay(UUID turbineUuid, LocalDateTime startDate) throws Exception {
        List<RemarkMetaEntity> remarkMetaEntities = remarkMetaRepository.findByGeneralOverviewEntityUuidOrderById(turbineUuid);

        if(remarkMetaEntities.isEmpty())
            throw new Exception("remark meta data empty");


        List<RemarkDataEntity> remarkDataEntities = remarkDataRepository.findByUuidAndTimestampOrderById(
                turbineUuid,
                remarkMetaEntities.stream().map(RemarkMetaEntity::getId).toList(),
                startDate,
                startDate.plusDays(1)
        );

        return DataMapper.convertToRemarkDomain(remarkDataEntities);
    }
}
