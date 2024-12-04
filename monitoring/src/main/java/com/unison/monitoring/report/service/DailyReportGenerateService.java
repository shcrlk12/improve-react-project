package com.unison.monitoring.report.service;

import com.unison.monitoring.report.dto.DailyReportDto;
import com.unison.monitoring.report.dto.ReportDto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DailyReportGenerateService {

    DailyReportDto generateDailyReport(UUID turbineUuid, LocalDateTime writeDate);
}
