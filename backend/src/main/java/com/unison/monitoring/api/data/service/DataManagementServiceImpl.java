package com.unison.monitoring.api.data.service;

import com.unison.monitoring.api.entity.DataEntity;
import com.unison.monitoring.api.data.DataRepository;
import com.unison.monitoring.api.domain.ReportData;
import com.unison.monitoring.api.entity.GeneralOverviewEntity;
import com.unison.monitoring.api.entity.GeneralOverviewRepository;
import com.unison.monitoring.util.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataManagementServiceImpl implements DataManagementService{

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
        Optional<GeneralOverviewEntity> optionalGeneralOverview = generalOverviewRepository.findById(firstReportData.getUuid());

        if(optionalGeneralOverview.isPresent()) {
            for (ReportData reportData : reportDataList) {

                dataEntityList.add(
                        DataEntity.builder()
                                .id(DataEntity.Id.builder()
                                        .timestamp(DateTimeUtils.parseISOLocalDateTime(reportData.getMeasuredDate()))
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
}
