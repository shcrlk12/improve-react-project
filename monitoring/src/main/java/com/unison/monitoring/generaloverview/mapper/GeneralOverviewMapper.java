package com.unison.monitoring.generaloverview.mapper;

import com.unison.common.dto.DailyReportRemarkDto;
import com.unison.common.dto.SitesDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.generaloverview.dto.GeneralOverviewDto;
import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeneralOverviewMapper {

    public static GeneralOverviewDto toDto(GeneralOverviewEntity generalOverviewEntity){
        return GeneralOverviewDto.builder()
                .turbineUuid(generalOverviewEntity.getUuid())
                .siteName(generalOverviewEntity.getSiteName())
                .ratedPower(generalOverviewEntity.getRatedPower())
                .description(generalOverviewEntity.getDescription())
                .remark(generalOverviewEntity.getRemark())
                .altitude(generalOverviewEntity.getAltitude())
                .hubHeight(generalOverviewEntity.getHubHeight())
                .commissionDate(generalOverviewEntity.getCommissionDate())
                .lastAlarmSyncDate(generalOverviewEntity.getLastAlarmSyncDate())
                .lastDataSyncDate(generalOverviewEntity.getLastDataSyncDate())
                .createdAt(generalOverviewEntity.getCreatedAt())
                .createdBy(generalOverviewEntity.getCreatedBy())
                .updatedAt(generalOverviewEntity.getUpdatedAt())
                .updatedBy(generalOverviewEntity.getUpdatedBy())
                .build();
    }

    public static Resource<DailyReportRemarkDto.Response> convertToDailyReportRemarkDtoResponse(ApiRequest<DailyReportRemarkDto.Request> dailyReportRemarkDtoRequest){
        Resource<DailyReportRemarkDto.Response> result = null;

        try{
            Resource<DailyReportRemarkDto.Request> data = dailyReportRemarkDtoRequest.getData();

            result = Resource.<DailyReportRemarkDto.Response>builder()
                    .id(data.getId())
                    .type(data.getType())
                    .attributes(DailyReportRemarkDto.Response.builder()
                            .remark(data.getAttributes().getRemark())
                            .build())
                    .build();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }

    public static List<GeneralOverviewDto> toDtoList(List<GeneralOverviewEntity> generalOverviewEntities){
        return generalOverviewEntities.stream()
                .map(GeneralOverviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<SitesDto.Response> toSiteDtoList(List<GeneralOverviewEntity> generalOverviewEntities){
        List<SitesDto.Response> result = null;

        try{
            result = generalOverviewEntities.stream()
                    .map(generalOverviewEntity -> new SitesDto.Response(generalOverviewEntity.getUuid(), generalOverviewEntity.getSiteName(), generalOverviewEntity.getRemark(), generalOverviewEntity.getRatedPower()))
                    .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }
}
