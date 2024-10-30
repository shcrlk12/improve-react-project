package com.unison.monitoring.api.mapper;

import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.common.jsonapi.response.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataMapper {

    public static List<ReportData> apiResponseToListReportData(ApiRequests<ReportDataDto.Response> response){
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

    public static List<ReportData> apiResponsesToListReportData(ApiResponses<ReportDataDto.Response> responses){
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

    public static List<AlarmDto.Response> apiResponsesToAlarmList(ApiResponses<AlarmDto.Response> responses){
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
