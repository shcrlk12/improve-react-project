package com.unison.batch.api;

import com.unison.batch.mapper.Mapper;
import com.unison.batch.service.U113UploadBatchService;
import com.unison.batch.service.UploadBatchService;
import com.unison.common.domain.ReportData;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.common.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class BatchController {
    private final UploadBatchService uploadBatchService;

    @GetMapping("/monitoring")
    public ResponseEntity<ApiRequests<ReportDataDto.Request>> Test(UUID turbineUuid, String targetDate){
        LocalDateTime startDate = DateTimeUtils.parseISOLocalDateTime(targetDate);
        List<ReportData> reportDataList = uploadBatchService.getReportData(startDate, startDate.plusDays(1));

        ApiRequests<ReportDataDto.Request> request = ApiRequests.<ReportDataDto.Request>builder()
                .data(Mapper.reportDataToResource(turbineUuid.toString(), reportDataList))
                .build();

        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(request);
    }
}
