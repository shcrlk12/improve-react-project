package com.unison.batch.mapper;

import com.unison.common.Constants;
import com.unison.common.domain.Alarm;
import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

public class Mapper {


    public static List<Resource<ReportDataDto.Response>> reportDataToResource(List<ReportData> reportDataList){
        List<Resource<ReportDataDto.Response>> result = new ArrayList<>();

        for (ReportData reportData : reportDataList) {
            Resource<ReportDataDto.Response> resource = Resource.<ReportDataDto.Response>builder()
                    .id(DateTimeUtils.parseLocalDateTime(reportData.getMeasureDate()).toString())
                    .type(ReportDataDto.TYPE)
                    .attributes(ReportDataDto.Response.builder()
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

    public static List<Resource<AlarmDto.Response>> alarmsToResource(List<Alarm> alarmList){
        List<Resource<AlarmDto.Response>> result = new ArrayList<>();

        for (Alarm alarm : alarmList) {
            Resource<AlarmDto.Response> resource = Resource.<AlarmDto.Response>builder()
                    .id(alarm.getAlarmId() + Constants.SEPARATOR + alarm.getAlarmLogTimestamp())
                    .type(AlarmDto.TYPE)
                    .attributes(AlarmDto.Response.builder()
                            .alarmNumber(alarm.getAlarmNumber())
                            .alarmCode(alarm.getAlarmCode())
                            .alarmLogTimestamp(alarm.getAlarmLogTimestamp())
                            .comment(alarm.getComment())
                            .build())
                    .build();
            result.add(resource);
        }

        return result;
    }
}
