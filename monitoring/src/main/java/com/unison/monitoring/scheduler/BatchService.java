package com.unison.monitoring.scheduler;

import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.response.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BatchService{


    Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU113();

    Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU113();

    Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU120();

    Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU151();

}
