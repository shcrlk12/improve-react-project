package com.unison.monitoring.api.data.service;


import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DataManagementService {
    LocalDateTime getLastUploadDate();

    void uploadData(List<ReportData> reportDataList) throws Exception;

    void uploadAlarms(List<AlarmDto.Response> alarmList, UUID turbineUuid) throws Exception;
}
