package com.unison.monitoring.api.mapper;

import com.unison.common.dto.ReportDataDto;
import com.unison.monitoring.api.domain.ReportData;
import com.unison.monitoring.api.jsonapi.Resource;
import com.unison.monitoring.api.jsonapi.request.ApiRequests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataMapper {

    public static List<ReportData> apiRequestsToListRequest(ApiRequests<ReportDataDto.Request> request){
        List<ReportData> response = new ArrayList<>();

        for(Resource<ReportDataDto.Request> resource : request.getData()){
            ReportDataDto.Request attribute = resource.getAttributes();

            response.add(ReportData.builder()
                            .uuid(UUID.fromString(resource.getType().split("_")[0]))
                            .measuredDate(resource.getId())
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

        return response;
    }
}
