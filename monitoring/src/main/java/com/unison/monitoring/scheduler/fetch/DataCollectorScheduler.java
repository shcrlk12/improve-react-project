package com.unison.monitoring.scheduler.fetch;

import com.unison.common.Constants;
import com.unison.monitoring.data.mapper.DataMapper;
import com.unison.monitoring.data.service.DataUploadService;
import com.unison.monitoring.scheduler.fetch.service.DataCollectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataCollectorScheduler {

    private final DataCollectorService dataCollectorService;
    private final DataUploadService dataUploadService;

    @Scheduled(cron = "30 * * * * *") // 0초에 가져오면 11:50분 데이터를 못가져 올 수 있음.
    public void run() throws Exception {

        //U113
        dataCollectorService.retrieveDataFromU113()
                .map(DataMapper::toReportDataList)
                .doOnNext(reportDataList -> {
                    try {
                        dataUploadService.uploadData(reportDataList, Constants.U113UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();

        dataCollectorService.retrieveAlarmsFromU113()
                .map(DataMapper::toAlarmResponseDtoList)
                .doOnNext(alarmList -> {
                    try {
                        dataUploadService.uploadAlarms(alarmList, Constants.U113UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();

        //U151
        dataCollectorService.retrieveDataFromU151()
                .map(DataMapper::toReportDataList)
                .doOnNext(reportDataList -> {
                    try {
                        dataUploadService.uploadData(reportDataList, Constants.U151UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();

        dataCollectorService.retrieveAlarmsFromU151()
                .map(DataMapper::toAlarmResponseDtoList)
                .doOnNext(alarmList -> {
                    try {
                        dataUploadService.uploadAlarms(alarmList, Constants.U151UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();

        //U120
        dataCollectorService.retrieveDataFromU120()
                .map(DataMapper::toReportDataList)
                .doOnNext(reportDataList -> {
                    try {
                        dataUploadService.uploadData(reportDataList, Constants.U120UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();

        dataCollectorService.retrieveAlarmsFromU120()
                .map(DataMapper::toAlarmResponseDtoList)
                .doOnNext(alarmList -> {
                    try {
                        dataUploadService.uploadAlarms(alarmList, Constants.U120UUID);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();
    }
}
