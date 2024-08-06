package com.unison.batch.scheduler;


import com.unison.batch.domain.ReportData;
import com.unison.batch.mapper.Mapper;
import com.unison.batch.jsonapi.request.ApiRequests;
import com.unison.batch.dto.ReportDataDto;
import com.unison.batch.service.U113UploadBatchService;
import com.unison.batch.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UploadScheduler {

    private final U113UploadBatchService u113UploadBatchService;

    @Scheduled(cron = "0 * * * * *")
    public void uploadMonitoringData () {
        //1. api 서버랑 통신해서, 몇일 이후로 값이 없는지 받음.
        u113UploadBatchService.retrieveLastUploadDate()
        //2. 현재 시간과 1번에서 얻은 값을 이용해서 DB에서 값을 가져옴 값을 읽어옴.
        .flatMap(lastUploadDto -> {
            LocalDateTime endDate = DateTimeUtils.formatAndParseLocalDateTime("yyyy-MM-dd 00:00:00", LocalDateTime.now());
            return u113UploadBatchService.getReportData(lastUploadDto.getLastUpdateTime(), endDate)
                    .map(reportDataList -> Tuples.of(lastUploadDto.getUuid(), reportDataList));
        })
        //3. api 서버로 json 형식으로 보냄.
        .subscribe(tuple -> {
            UUID uuid = tuple.getT1();
            List<ReportData> reportDataList = tuple.getT2();  // reportDataList

            ApiRequests<ReportDataDto.Request> request = ApiRequests.<ReportDataDto.Request>builder()
                    .data(Mapper.reportDataToResource(uuid.toString(), reportDataList))
                    .build();

            u113UploadBatchService.uploadData(request);
        }, Throwable::printStackTrace);

    }
}
