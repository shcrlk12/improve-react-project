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
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/monitoring")
    public ResponseEntity<ApiResponses<ReportDataDto.Response>> Test(String startTime, String endTime){
        LocalDateTime startDate = DateTimeUtils.parseLocalDateTime(startTime);
        LocalDateTime endDate;

        if(endTime == null){
            endDate = startDate.plusDays(1);
        }else{
            endDate = DateTimeUtils.parseLocalDateTime(endTime);
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
    public ResponseEntity<ApiResponses<AlarmDto.Response>> Test2(String startTime, String endTime){
        LocalDateTime startDate = DateTimeUtils.parseLocalDateTime(startTime);
        LocalDateTime endDate;
 
        if(endTime == null){
            endDate = startDate.plusDays(1);
        }else{
            endDate = DateTimeUtils.parseLocalDateTime(endTime);
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
