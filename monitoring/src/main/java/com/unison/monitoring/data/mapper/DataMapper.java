package com.unison.monitoring.data.mapper;

import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.common.jsonapi.response.ApiResponses;
import com.unison.monitoring.common.dto.TimeChart;
import com.unison.monitoring.data.dto.DataDto;
import com.unison.monitoring.data.entity.DataEntity;
import com.unison.monitoring.powercurve.dto.PowerCurveDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataMapper {

    public static List<PowerCurveDto> toPowerCurveDto(List<DataEntity> dataEntities){

        return dataEntities.stream()
                .filter(dataEntity -> dataEntity.getFullPerformance() == 600)
                .map(DataMapper::toPowerCurveDto)
                .collect(Collectors.toList());
    }

    public static PowerCurveDto toPowerCurveDto(DataEntity dataEntity){

        return new PowerCurveDto(
                dataEntity.getId().getGeneralOverviewEntity().getUuid(),
                String.valueOf(dataEntity.getWindSpeed()),
                String.valueOf(dataEntity.getActivePower())
        );
    }

    public static List<DataDto> toDtoList(List<DataEntity> dataEntities){

        return dataEntities.stream()
                .map(DataMapper::toDto)
                .collect(Collectors.toList());
    }

    public static DataDto toDto(DataEntity dataEntity){

        return DataDto.builder()
                .turbineUuid(dataEntity.getId().getGeneralOverviewEntity().getUuid())
                .timestamp(dataEntity.getId().getTimestamp())
                .fullPerformance(dataEntity.getFullPerformance())
                .partialPerformance(dataEntity.getPartialPerformance())
                .outOfElectrical(dataEntity.getOutOfElectrical())
                .outOfEnvironment(dataEntity.getOutOfEnvironment())
                .requestedShutdown(dataEntity.getRequestedShutdown())
                .scheduledMaintenance(dataEntity.getScheduledMaintenance())
                .technicalStandby(dataEntity.getTechnicalStandby())
                .rotorSpeed(dataEntity.getRotorSpeed())
                .windSpeed(dataEntity.getWindSpeed())
                .nacOutTmp(dataEntity.getNacOutTmp())
                .activePower(dataEntity.getActivePower())
                .createdAt(dataEntity.getCreatedAt())
                .build();
    }
    public static List<TimeChart> toTimeChart(List<DataEntity> dataEntities){

        return dataEntities.stream()
                .map(dataEntity -> new TimeChart(
                        dataEntity.getId().getTimestamp(),
                        String.valueOf(dataEntity.getWindSpeed()),
                        String.valueOf(dataEntity.getActivePower()),
                        String.valueOf(dataEntity.getRotorSpeed()))
                )
                .collect(Collectors.toList());
    }

    public static List<ReportData> toReportDataList(ApiRequests<ReportDataDto.Response> response){
        List<ReportData> result = new ArrayList<>();

        for(Resource<ReportDataDto.Response> resource : response.getData()){
            ReportDataDto.Response attribute = resource.getAttributes();

            ReportData.uuid = UUID.fromString(resource.getType().split("_")[0]);

            result.add(ReportData.builder()
                            .measureDate(resource.getId())
                            .fullPerformance(attribute.getFullPerformance())
                            .partialPerformance(attribute.getPartialPerformance())
                            .outOfElectrical(attribute.getOutOfElectrical())
                            .outOfEnvironment(attribute.getOutOfEnvironment())
                            .requestedShutdown(attribute.getRequestedShutdown())
                            .scheduledMaintenance(attribute.getScheduledMaintenance())
                            .technicalStandby(attribute.getTechnicalStandby())
                            .rotorSpeed(attribute.getRotorSpeed())
                            .windSpeed(attribute.getWindSpeed())
                            .nacOutTmp(attribute.getNacOutTmp())
                            .activePower(attribute.getActivePower())
                            .build());
        }

        return result;
    }

    public static List<ReportData> toReportDataList(ApiResponses<ReportDataDto.Response> responses){
        List<ReportData> result = new ArrayList<>();

        for(Resource<ReportDataDto.Response> resource : responses.getData()){
            ReportDataDto.Response attribute = resource.getAttributes();

            result.add(ReportData.builder()
                    .measureDate(resource.getId())
                    .fullPerformance(attribute.getFullPerformance())
                    .partialPerformance(attribute.getPartialPerformance())
                    .outOfElectrical(attribute.getOutOfElectrical())
                    .outOfEnvironment(attribute.getOutOfEnvironment())
                    .requestedShutdown(attribute.getRequestedShutdown())
                    .scheduledMaintenance(attribute.getScheduledMaintenance())
                    .technicalStandby(attribute.getTechnicalStandby())
                    .rotorSpeed(attribute.getRotorSpeed())
                    .windSpeed(attribute.getWindSpeed())
                    .nacOutTmp(attribute.getNacOutTmp())
                    .activePower(attribute.getActivePower())
                    .build());
        }

        return result;
    }

    public static List<AlarmDto.Response> toAlarmResponseDtoList(ApiResponses<AlarmDto.Response> responses){
        List<AlarmDto.Response> result = new ArrayList<>();

        for(Resource<AlarmDto.Response> resource : responses.getData()){
            AlarmDto.Response attribute = resource.getAttributes();

            result.add(AlarmDto.Response.builder()
                            .alarmCode(attribute.getAlarmCode())
                            .alarmNumber(attribute.getAlarmNumber())
                            .alarmLogTimestamp(attribute.getAlarmLogTimestamp())
                            .comment(attribute.getComment())
                    .build());
        }

        return result;
    }
}
