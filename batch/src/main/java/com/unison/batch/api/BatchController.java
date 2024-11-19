package com.unison.batch.api;

import com.unison.batch.mapper.Mapper;
import com.unison.batch.service.UploadBatchService;
import com.unison.common.domain.Alarm;
import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.response.ApiResponses;
import com.unison.common.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class BatchController {
    private final UploadBatchService uploadBatchService;

    @Getter
    @AllArgsConstructor
    public static class TimeObject {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
    @GetMapping("/monitoring")
    public ResponseEntity<ApiResponses<ReportDataDto.Response>> Test(TimeObject timeObject){
        LocalDateTime startDate = timeObject.getStartTime();
        LocalDateTime endDate = timeObject.getEndTime();

        if(endDate == null){
            endDate = startDate.plusDays(1);
        }

        List<ReportData> reportDataList = uploadBatchService.getReportData(startDate, endDate);

        ApiResponses<ReportDataDto.Response> request = ApiResponses.<ReportDataDto.Response>builder()
                .data(Mapper.reportDataToResource(reportDataList))
                .build();

        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(request);
    }

    @GetMapping("/alarms")
    public ResponseEntity<ApiResponses<AlarmDto.Response>> Test2(TimeObject timeObject){
        LocalDateTime startDate = timeObject.getStartTime();
        LocalDateTime endDate = timeObject.getEndTime();

        if(endDate == null){
            endDate = startDate.plusDays(1);
        }

        List<Alarm> reportDataList = uploadBatchService.getAlarms(startDate, endDate);

        ApiResponses<AlarmDto.Response> request = ApiResponses.<AlarmDto.Response>builder()
                .data(Mapper.alarmsToResource(reportDataList))
                .build();

        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(request);
    }
}
