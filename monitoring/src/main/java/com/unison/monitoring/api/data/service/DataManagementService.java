package com.unison.monitoring.api.data.service;


import com.unison.common.domain.ReportData;

import java.time.LocalDateTime;
import java.util.List;

public interface DataManagementService {
    LocalDateTime getLastUploadDate();

    void uploadData(List<ReportData> reportDataList) throws Exception;
}
