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
        return retrieveAlarms(batchServerProperties.getU113Domain(), Constants.U113UUID);

    }

    @Override
    public Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU120() throws Exception {
        return null;
    }

    @Override
    public Mono<ApiResponses<AlarmDto.Response>> retrieveAlarmsFromU151() throws Exception {
        return retrieveAlarms(batchServerProperties.getU151Domain(), Constants.U151UUID);

    }

    @Override
    @Transactional
    public Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU113() throws Exception {

        return retrieveData(batchServerProperties.getU113Domain(), Constants.U113UUID);

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
    public Mono<ApiResponses<ReportDataDto.Response>> retrieveDataFromU151() throws Exception {

        return retrieveData(batchServerProperties.getU151Domain(), Constants.U151UUID);
    }

    @Transactional
    private Mono<ApiResponses<AlarmDto.Response>> retrieveAlarms(String serverDomain, UUID turbineUuid) throws Exception {
        GeneralOverviewEntity generalOverviewEntity = getTurbineAlarmsLastSyncDate(turbineUuid);

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

        LocalDateTime nextMonthDate = lastSyncDate.plusMonths(1);
        LocalDateTime endTime;
        if(nextMonthDate.isBefore(today))
            endTime = nextMonthDate;
        else
            endTime = today;

        return webClientBuilder
                .baseUrl("http://" + serverDomain)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/alarms")
                        .queryParam("startTime", lastSyncDate)
                        .queryParam("endTime", endTime)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<AlarmDto.Response>>() {})
                .doOnSuccess(response -> {
                    generalOverviewEntity.setLastAlarmSyncDate(endTime);
                    generalOverviewRepository.save(generalOverviewEntity);
                });
    }

    @Transactional
    private Mono<ApiResponses<ReportDataDto.Response>> retrieveData(String serverDomain, UUID turbineUuid) throws Exception {
        GeneralOverviewEntity generalOverviewEntity = getTurbineDataLastSyncDate(turbineUuid);

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

        LocalDateTime nextMonthDate = lastSyncDate.plusMonths(1);
        LocalDateTime endTime;
        if(nextMonthDate.isBefore(today))
            endTime = nextMonthDate;
        else
            endTime = today;

        return webClientBuilder
                .baseUrl("http://" + serverDomain)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/data/monitoring")
                        .queryParam("startTime", lastSyncDate)
                        .queryParam("endTime", endTime)
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new Exception("Server Error"))
                )
                .bodyToMono(new ParameterizedTypeReference<ApiResponses<ReportDataDto.Response>>() {})
                .doOnSuccess(response -> {
                    generalOverviewEntity.setLastDataSyncDate(endTime);
                    generalOverviewRepository.save(generalOverviewEntity);
                });
    }
}
