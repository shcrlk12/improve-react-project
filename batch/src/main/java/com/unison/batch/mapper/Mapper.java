package com.unison.batch.mapper;

import com.unison.batch.jsonapi.Resource;
import com.unison.batch.domain.ReportData;
import com.unison.batch.dto.ReportDataDto;

import java.util.ArrayList;
import java.util.List;

public class Mapper {


    public static List<Resource<ReportDataDto.Request>> reportDataToResource(String turbineType, List<ReportData> reportDataList){
        List<Resource<ReportDataDto.Request>> result = new ArrayList<>();

        for (ReportData reportData : reportDataList) {
            Resource<ReportDataDto.Request> resource = Resource.<ReportDataDto.Request>builder()
                    .id(reportData.getMeasureDate())
                    .type(turbineType + "-" + ReportDataDto.TYPE)
                    .attributes(ReportDataDto.Request.builder()
                            .fullPerformance(reportData.getFullPerformance())
                            .partialPerformance(reportData.getPartialPerformance())
                            .outOfElectrical(reportData.getOutOfElectrical())
                            .outOfEnvironment(reportData.getOutOfEnvironment())
                            .requestedShutdown(reportData.getRequestedShutdown())
                            .scheduledMaintenance(reportData.getScheduledMaintenance())
                            .technicalStandby(reportData.getTechnicalStandby())
                            .rotorSpeed(reportData.getRotorSpeed())
                            .windSpeed(reportData.getWindSpeed())
                            .nacOutTmp(reportData.getNacOutTmp())
                            .activePower(reportData.getActivePower())
                            .build())
                    .build();
            result.add(resource);
        }

        return result;
    }
}
