package com.unison.monitoring.scheduler.fetch.service;

import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.response.ApiResponses;
import reactor.core.publisher.Mono;

public interface DataCollectorService {


    Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU113() throws Exception;

    Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU120() throws Exception;

    Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU151() throws Exception;


    Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU113() throws Exception;

    Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU120() throws Exception;

    Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU151() throws Exception;

}
