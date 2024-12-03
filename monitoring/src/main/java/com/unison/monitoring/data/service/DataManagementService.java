package com.unison.monitoring.api.data.service;


import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.DailyReportRemarkDto;
import com.unison.common.dto.RemarkDto;
import com.unison.common.dto.SitesDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.monitoring.api.data.dto.ReportDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DataManagementService {
    LocalDateTime getLastUploadDate();

    void uploadData(List<ReportData> reportDataList, UUID turbineUuid) throws Exception;

    void uploadAlarms(List<AlarmDto.Response> alarmList, UUID turbineUuid) throws Exception;

    ReportDto.Response createAndWriteHeaderTable(UUID turbineUuid, LocalDateTime writeDate) throws Exception;
    List<ReportDto.PowerCurve> getReferencePowerCurve(UUID turbineUuid);

    List<ReportDto.PowerCurve> getPowerCurveByTime(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate);

    List<ReportDto.TimeChart> getTimeChartDataList(UUID turbineUuid, LocalDateTime date);

    List<ReportDto.Alarm> getAlarmsByTime(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate);

    List<ReportDto.Remark> getRemarksByTime(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate);

    List<SitesDto.Response> getSites();

    List<Resource<RemarkDto.Response>> createRemarks(ApiRequests<RemarkDto.Request> request) throws Exception;

    List<Resource<RemarkDto.Response>> updateRemarks(ApiRequests<RemarkDto.Request> request) throws Exception;

    Resource<DailyReportRemarkDto.Response> updateDailyReportRemark(ApiRequest<DailyReportRemarkDto.Request> request) throws Exception;


}
