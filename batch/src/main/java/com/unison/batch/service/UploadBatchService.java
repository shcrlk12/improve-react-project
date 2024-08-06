package com.unison.batch.service;

import com.unison.batch.dto.LastUpdateDto;
import com.unison.batch.jsonapi.request.ApiRequests;
import com.unison.batch.domain.ReportData;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface UploadBatchService {

    Mono<LastUpdateDto.Response> retrieveLastUploadDate();

    void uploadData(ApiRequests<?> request);

    Mono<List<ReportData>> getReportData(LocalDateTime startDate, LocalDateTime endDate);
}
