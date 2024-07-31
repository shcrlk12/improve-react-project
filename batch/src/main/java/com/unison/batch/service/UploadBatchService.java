package com.unison.batch.service;

import com.unison.batch.jsonapiorg.request.ApiRequests;
import com.unison.batch.model.ReportData;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface UploadBatchService {

    Mono<LocalDateTime> retrieveLastUploadDate();

    void uploadData(ApiRequests<?> request);

    Mono<List<ReportData>> getReportData(LocalDateTime startDate, LocalDateTime endDate);
}
