package com.unison.batch.service;

import com.unison.common.domain.ReportData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class U120UploadBatchService implements UploadBatchService {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<ReportData> getReportData(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
