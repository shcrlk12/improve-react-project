package com.unison.monitoring.data.service;

import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;

import java.util.List;
import java.util.UUID;

public interface DataUploadService {

    void uploadData(List<ReportData> reportDataList, UUID turbineUuid) throws Exception;

    void uploadAlarms(List<AlarmDto.Response> alarmList, UUID turbineUuid) throws Exception;
}
