package com.unison.monitoring.api.data.service;

import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.api.entity.AlarmEntity;
import com.unison.monitoring.api.entity.DataEntity;
import com.unison.monitoring.api.repository.AlarmRepository;
import com.unison.monitoring.api.repository.DataRepository;
import com.unison.monitoring.api.entity.GeneralOverviewEntity;
import com.unison.monitoring.api.entity.GeneralOverviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataManagementServiceImpl implements DataManagementService{

    private final AlarmRepository alarmRepository;
    private final DataRepository dataRepository;
    private final GeneralOverviewRepository generalOverviewRepository;

    @Override
    public LocalDateTime getLastUploadDate() {
        return null;
    }

    @Override
    public void uploadData(List<ReportData> reportDataList) throws Exception {

        List<DataEntity> dataEntityList = new ArrayList<>();

        //find uuid of report data
        Optional<ReportData> optionalReportData = reportDataList.stream().findFirst();
        ReportData firstReportData = optionalReportData.orElseThrow(Exception::new);
        Optional<GeneralOverviewEntity> optionalGeneralOverview = generalOverviewRepository.findById(ReportData.uuid);

        if(optionalGeneralOverview.isPresent()) {
            for (ReportData reportData : reportDataList) {

                dataEntityList.add(
                        DataEntity.builder()
                                .id(DataEntity.Id.builder()
                                        .timestamp(DateTimeUtils.parseISOLocalDateTime(reportData.getMeasureDate()))
                                        .generalOverview(optionalGeneralOverview.get())
                                        .build())
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
                                .build()
                );
            }
            dataRepository.saveAll(dataEntityList);
        }
    }

    @Override
    public void uploadAlarms(List<AlarmDto.Response> alarmList, UUID turbineUuid) throws Exception {
        List<AlarmEntity> alarmEntityList = new ArrayList<>();

        if(alarmList == null || alarmList.isEmpty()){
            throw new Exception();
        }

        if(generalOverviewRepository.findById(turbineUuid).isPresent()) {
            for (AlarmDto.Response alarm : alarmList) {

                alarmEntityList.add(
                        AlarmEntity.builder()
                                .id(
                                        AlarmEntity.Id.builder()
                                                .timestamp(DateTimeUtils.parseLocalDateTime(alarm.getAlarmLogTimestamp()))
                                                .alarmNumber(alarm.getAlarmNumber())
                                                .turbine_uuid(turbineUuid)
                                                .build()
                                )
                                .alarmCode(alarm.getAlarmCode())
                                .comment(alarm.getComment())
                                .build()
                );
            }
            alarmRepository.saveAll(alarmEntityList);
        }
    }
}
