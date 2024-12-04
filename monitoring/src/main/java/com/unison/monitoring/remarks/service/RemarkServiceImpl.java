package com.unison.monitoring.remarks.service;

import com.unison.common.Constants;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.monitoring.common.security.UserDetailImpl;
import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import com.unison.monitoring.remarks.dto.RemarkDto;
import com.unison.monitoring.remarks.entity.RemarkDataEntity;
import com.unison.monitoring.remarks.entity.RemarkMetaEntity;
import com.unison.monitoring.remarks.mapper.RemarkMapper;
import com.unison.monitoring.remarks.repository.RemarkDataRepository;
import com.unison.monitoring.remarks.repository.RemarkMetaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemarkServiceImpl implements RemarkService {
    private final RemarkDataRepository remarkDataRepository;
    private final RemarkMetaRepository remarkMetaRepository;

    @Override
    public List<RemarkDto> getRemarks(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate) {
        List<RemarkMetaEntity> remarkMetaEntities = remarkMetaRepository.findByGeneralOverviewEntityUuidOrderByOrderId(turbineUuid);

        List<RemarkDataEntity> remarkEntities = remarkDataRepository.findByUuidAndTimestampOrderById(turbineUuid, startDate, endDate);

        if(remarkEntities.isEmpty()){
            remarkEntities = remarkMetaEntities.stream()
                    .map(remarkMetaEntity ->
                            RemarkDataEntity.builder()
                                    .uuid(Constants.DEFAULT_UUID)
                                    .remarkMetaEntity(remarkMetaEntity)
                                    .timestamp(startDate)
                                    .description(remarkMetaEntity.getDefaultDescription())
                                    .build()
                    )
                    .toList();
        }
        return RemarkMapper.toDto(remarkEntities);
    }

    @Override
    public List<Resource<com.unison.common.dto.RemarkDto.Response>> createRemarks(ApiRequests<com.unison.common.dto.RemarkDto.Request> request) {
        List<Resource<com.unison.common.dto.RemarkDto.Response>> response;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();


        UUID turbineUuid = request.getData().stream()
                .findFirst()
                .orElseThrow()
                .getAttributes()
                .getTurbineUuid();

        List<RemarkMetaEntity> remarkMetaEntities = remarkMetaRepository.findByGeneralOverviewEntityUuidOrderByOrderId(turbineUuid);

        if(remarkMetaEntities.isEmpty())
            throw new RuntimeException("Meta Entity empty");

        List<RemarkDataEntity> remarkDataEntities = request.getData().stream()
                .map(dataInRequest ->
                        RemarkDataEntity.builder()
                                .uuid(UUID.fromString(dataInRequest.getId()))
                                .timestamp(dataInRequest.getAttributes().getTimestamp())
                                .description(dataInRequest.getAttributes().getContent())
                                .remarkMetaEntity(
                                        remarkMetaEntities.stream()
                                                .filter(remarkMetaEntity -> remarkMetaEntity.getTitle().equals(dataInRequest.getAttributes().getTitle()))
                                                .findFirst()
                                                .orElseThrow()
                                )
                                .createdBy(userDetail.getMember().getId())
                                .createdAt(LocalDateTime.now())
                                .generalOverviewEntity(new GeneralOverviewEntity(dataInRequest.getAttributes().getTurbineUuid()))
                                .build()
                ).toList();

        remarkDataRepository.saveAll(remarkDataEntities);

        response = RemarkMapper.toRemarkResponseDtoList(request);

        return response;
    }

    @Override
    @Transactional
    public List<Resource<com.unison.common.dto.RemarkDto.Response>> updateRemarks(ApiRequests<com.unison.common.dto.RemarkDto.Request> request) {
        List<Resource<com.unison.common.dto.RemarkDto.Response>> response;

        List<RemarkDataEntity> changeRemarkDataEntities = request.getData().stream()
                .map(dataInRequest -> RemarkDataEntity.builder()
                        .uuid(UUID.fromString(dataInRequest.getId()))
                        .description(dataInRequest.getAttributes().getContent())
                        .build())
                .toList();

        List<RemarkDataEntity> originRemarkDataEntities = remarkDataRepository.findByUuidIn(
                changeRemarkDataEntities.stream()
                        .map(RemarkDataEntity::getId)
                        .toList()
        );

        List<RemarkDataEntity> updateRemarkDataEntities = originRemarkDataEntities.stream()
                .map(remarkDataEntity ->
                        {
                            remarkDataEntity.setDescription(
                                    changeRemarkDataEntities.stream()
                                            .filter(changeRemarkDataEntity ->
                                                    changeRemarkDataEntity.getUuid().equals(remarkDataEntity.getId())
                                            )
                                            .findFirst()
                                            .orElseThrow()
                                            .getDescription()
                            );
                            return remarkDataEntity;
                        }
                )
                .toList();

        remarkDataRepository.saveAll(updateRemarkDataEntities);

        response = RemarkMapper.toRemarkResponseDtoList(request);

        return response;
    }
}
