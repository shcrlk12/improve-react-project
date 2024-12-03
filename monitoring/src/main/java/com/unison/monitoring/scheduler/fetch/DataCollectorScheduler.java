package com.unison.monitoring.scheduler;

import com.unison.common.Constants;
import com.unison.monitoring.data.service.DataManagementService;
import com.unison.monitoring.data.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final BatchService batchService;
    private final DataManagementService dataManagementService;

    //10분 마다 실행.
//    @Scheduled(cron = "0 0/10 * * * *")
    @Scheduled(cron = "30 * * * * *") // 0초에 가져오면 11:50분 데이터를 못가져 올 수 있음.
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
                .block();

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

        //U151
        batchService.retrieveDataFromU151()
                .map(DataMapper::apiResponsesToListReportData)
                .doOnNext(reportDataList -> {
                    try {
                        dataManagementService.uploadData(reportDataList, Constants.U151UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();

        batchService.retrieveAlarmsFromU151()
                .map(DataMapper::apiResponsesToAlarmList)
                .doOnNext(alarmList -> {
                    try {
                        dataManagementService.uploadAlarms(alarmList, Constants.U151UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();

        //U120
        batchService.retrieveDataFromU120()
                .map(DataMapper::apiResponsesToListReportData)
                .doOnNext(reportDataList -> {
                    try {
                        dataManagementService.uploadData(reportDataList, Constants.U120UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();

        batchService.retrieveAlarmsFromU120()
                .map(DataMapper::apiResponsesToAlarmList)
                .doOnNext(alarmList -> {
                    try {
                        dataManagementService.uploadAlarms(alarmList, Constants.U120UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe();
    }
}
