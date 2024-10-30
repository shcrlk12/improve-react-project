package com.unison.monitoring.scheduler;

import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.response.ApiResponse;
import com.unison.common.jsonapi.response.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService{

    @Override
    public Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU113() {
        return WebClient.create("http://127.0.0.1:5151")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/alarms")
                        .queryParam("startTime", "2024-05-01 00:00") // 쿼리 파라미터 추가
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<AlarmDto.Response>>() {});
    }

    @Override
    public Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU113() {
        return WebClient.create("http://127.0.0.1:5151")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/monitoring")
                        .queryParam("startTime", "2024-05-01 00:00") // 쿼리 파라미터 추가
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<ReportDataDto.Response>>() {});
    }


    @Override
    public Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU120() {
        return WebClient.create("http://127.0.0.1:5151")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/monitoring")
                        .queryParam("targetDate", "2024-05-01 00:00") // 쿼리 파라미터 추가
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<ReportDataDto.Response>>() {});
    }

    @Override
    public Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU151() {
        return WebClient.create("http://127.0.0.1:5151")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/monitoring")
                        .queryParam("targetDate", "2024-05-01 00:00") // 쿼리 파라미터 추가
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<ReportDataDto.Response>>() {});
    }
}
