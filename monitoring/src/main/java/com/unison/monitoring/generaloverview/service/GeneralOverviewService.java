package com.unison.monitoring.generaloverview.service;

import com.unison.common.dto.DailyReportRemarkDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.generaloverview.dto.GeneralOverviewDto;

import java.util.List;
import java.util.UUID;

public interface GeneralOverviewService {
    GeneralOverviewDto findByTurbineUuid(UUID turbineUuid);

    List<GeneralOverviewDto> findAll();


}
