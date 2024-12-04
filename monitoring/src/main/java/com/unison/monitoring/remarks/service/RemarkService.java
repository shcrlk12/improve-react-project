package com.unison.monitoring.remarks.service;


import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.monitoring.remarks.dto.RemarkDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RemarkService {
    List<RemarkDto> getRemarks(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate);

    List<Resource<com.unison.common.dto.RemarkDto.Response>> createRemarks(ApiRequests<com.unison.common.dto.RemarkDto.Request> request);

    List<Resource<com.unison.common.dto.RemarkDto.Response>> updateRemarks(ApiRequests<com.unison.common.dto.RemarkDto.Request> request);
}
