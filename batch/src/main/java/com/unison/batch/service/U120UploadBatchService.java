package com.unison.batch.service;

import com.unison.batch.dto.LastUpdateDto;
import com.unison.batch.jsonapi.request.ApiRequests;
import com.unison.batch.domain.ReportData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class U120UploadBatchService implements UploadBatchService {
    @Override
    public Mono<LastUpdateDto.Response> retrieveLastUploadDate() {
        return null;
    }

    @Override
    public void uploadData(ApiRequests<?> request) {

    }

    @Override
    public Mono<List<ReportData>> getReportData(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
