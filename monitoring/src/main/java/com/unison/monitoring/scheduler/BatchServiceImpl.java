package com.unison.monitoring.scheduler;

import com.unison.common.Constants;
import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.ReportDataDto;
import com.unison.common.jsonapi.response.ApiResponses;
import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.api.entity.GeneralOverviewEntity;
import com.unison.monitoring.api.entity.GeneralOverviewRepository;
import com.unison.monitoring.config.BatchServerProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService{

    private final BatchServerProperties batchServerProperties;
    private final GeneralOverviewRepository generalOverviewRepository;
    private final WebClient.Builder webClientBuilder;

    private GeneralOverviewEntity getTurbineDataLastSyncDate(UUID turbineUuid) throws Exception {

        return generalOverviewRepository.findById(turbineUuid)
                .orElseThrow(() -> new Exception("not found turbine uuid " + turbineUuid));
    }

    private GeneralOverviewEntity getTurbineAlarmsLastSyncDate(UUID turbineUuid) throws Exception {

        return generalOverviewRepository.findById(turbineUuid)
                .orElseThrow(() -> new Exception("not found turbine uuid " + turbineUuid));
    }

    @Override
    @Transactional
    public Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU113() throws Exception {
        String serverDomain = batchServerProperties.getU113Domain();

        GeneralOverviewEntity generalOverviewEntity = getTurbineAlarmsLastSyncDate(Constants.U113UUID);

        LocalDateTime lastSyncDate = generalOverviewEntity.getLastAlarmSyncDate();
        LocalDateTime today = LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                0,
                0
        );

        if(lastSyncDate.isEqual(today))
            return Mono.empty();

        return webClientBuilder
                    .baseUrl("http://" + serverDomain)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/alarms")
                        .queryParam("startTime", lastSyncDate)
                        .queryParam("endTime", today)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<AlarmDto.Response>>() {})
                .doOnSuccess(response -> {
                    generalOverviewEntity.setLastAlarmSyncDate(today);
                    generalOverviewRepository.save(generalOverviewEntity);
                });
    }

    @Override
    @Transactional
    public Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU113() throws Exception {

        String serverDomain = batchServerProperties.getU113Domain();

        GeneralOverviewEntity generalOverviewEntity = getTurbineDataLastSyncDate(Constants.U113UUID);

        LocalDateTime lastSyncDate = generalOverviewEntity.getLastDataSyncDate();
        LocalDateTime today = LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                0,
                0
        );

        if(lastSyncDate.isEqual(today))
            return Mono.empty();

        return webClientBuilder
                .baseUrl("http://" + serverDomain)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/monitoring")
                        .queryParam("startTime", lastSyncDate)
                        .queryParam("endTime", today)
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new Exception("Server Error"))
                )
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<ReportDataDto.Response>>() {})
                .doOnSuccess(response -> {
                    generalOverviewEntity.setLastDataSyncDate(today);
                    generalOverviewRepository.save(generalOverviewEntity);
                });
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
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new Exception("Server Error"))
                )
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<ReportDataDto.Response>>() {})
                ;
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
