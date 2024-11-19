package com.unison.monitoring.scheduler;

import com.unison.common.Constants;
import com.unison.monitoring.api.data.service.DataManagementService;
import com.unison.monitoring.api.mapper.DataMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final BatchService batchService;
    private final DataManagementService dataManagementService;

    //10분 마다 실행.
    @Scheduled(cron = "0 * * * * *")
    public void run() throws Exception {

        //U113
        batchService.retrieveDataFromU113()
                .map(DataMapper::apiResponsesToListReportData)
                .doOnNext(reportDataList -> {
                    try {
                        dataManagementService.uploadData(reportDataList, Constants.U113UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();

        batchService.retrieveAlarmsFromU113()
                .map(DataMapper::apiResponsesToAlarmList)
                .doOnNext(alarmList -> {
                    try {
                        dataManagementService.uploadAlarms(alarmList, Constants.U113UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();

//        //U113
//        batchService.retrieveDataFromU113(date2)
//                .map(DataMapper::apiResponsesToListReportData)
//                .doOnNext(reportDataList -> {
//                    try {
//                        dataManagementService.uploadData(reportDataList, Constants.U113UUID);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe();
//
//        batchService.retrieveAlarmsFromU113(date2)
//                .map(DataMapper::apiResponsesToAlarmList)
//                .doOnNext(alarmList -> {
//                    try {
//                        dataManagementService.uploadAlarms(alarmList, Constants.U113UUID);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe();
//
//        //U113
//        batchService.retrieveDataFromU113(date3)
//                .map(DataMapper::apiResponsesToListReportData)
//                .doOnNext(reportDataList -> {
//                    try {
//                        dataManagementService.uploadData(reportDataList, Constants.U113UUID);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe();
//
//        batchService.retrieveAlarmsFromU113(date3)
//                .map(DataMapper::apiResponsesToAlarmList)
//                .doOnNext(alarmList -> {
//                    try {
//                        dataManagementService.uploadAlarms(alarmList, Constants.U113UUID);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe();
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
