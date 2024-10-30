package com.unison.monitoring.scheduler;

import com.unison.common.Constants;
import com.unison.common.domain.ReportData;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.response.ApiResponses;
import com.unison.monitoring.api.data.service.DataManagementService;
import com.unison.monitoring.api.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final BatchService batchService;
    private final DataManagementService dataManagementService;
//    @Scheduled(cron = "* * * * * *")
    @Scheduled(fixedDelay = 1000000)
    public void run() {
        //U113
        batchService.retrieveDataFromU113()
                .map(DataMapper::apiResponsesToListReportData)
                .doOnNext(reportDataList -> {
                    try {
                        ReportData.uuid = Constants.u113UUID;
                        dataManagementService.uploadData(reportDataList);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();

        batchService.retrieveAlarmsFromU113()
                .map(DataMapper::apiResponsesToAlarmList)
                .doOnNext(alarmList -> {
                    try {
                        dataManagementService.uploadAlarms(alarmList, Constants.u113UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();
//        //U120
//        batchService.retrieveDataFromU120()
//                .map(DataMapper::apiResponsesToListReportData)
//                .doOnNext(reportDataList -> {
//                    try {
//                        ReportData.uuid = Constants.u120UUID;
//                        dataManagementService.uploadData(reportDataList);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe();
//
//        //U151
//        batchService.retrieveDataFromU151()
//                .map(DataMapper::apiResponsesToListReportData)
//                .doOnNext(reportDataList -> {
//                    try {
//                        ReportData.uuid = Constants.u151UUID;
//                        dataManagementService.uploadData(reportDataList);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe();
    }
}
