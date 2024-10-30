package com.unison.batch.service;

import com.unison.common.domain.Alarm;
import com.unison.common.domain.ReportData;

import java.time.LocalDateTime;
import java.util.List;

public interface UploadBatchService {

    List<ReportData> getReportData(LocalDateTime startDate, LocalDateTime endDate);

    List<Alarm> getAlarms(LocalDateTime startDate, LocalDateTime endDate);
}
