package com.unison.batch.service;

import com.unison.batch.jsonapiorg.request.ApiRequests;
import com.unison.batch.jsonapiorg.response.ApiResponse;
import com.unison.batch.model.TimeDto;
import com.unison.batch.model.ReportData;
import com.unison.batch.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class U113UploadBatchService implements UploadBatchService {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    @Override
    public Mono<LocalDateTime> retrieveLastUploadDate() {

        return WebClient.create("http://localhost:1234")
                .get()
                .uri(URI.create("/api/data/last-update"))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<TimeDto.Response>>() {})
                .map(responseEntity ->
                    DateTimeUtils.parseLocalDateTime("yyyy-MM-dd HH:mm:ss", responseEntity.getBody().getData().getAttributes().getTime())
                );
    }

    @Override
    public void uploadData(ApiRequests<?> request) {
        WebClient.create("http://localhost:1234")
                .post()
                .uri(URI.create("/api/data"))
                .body(request , ApiRequests.class)
                .retrieve();
    }

    @Override
    public Mono<List<ReportData>> getReportData(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT " +
                "measure_date " +
                "st_fullPerTm, " +
                "st_parPerTm, " +
                "st_tecStdbyTm, " +
                "st_outEnvTm, " +
                "st_reqShtdnTm, " +
                "st_outElecSpTm, " +
                "st_schMtncTm, " +
                "rot_RotSpd, " +
                "nac_WdSpdNac1sNTFAvg, " +
                "nac_NacOutTmp, " +
                "tur_TotWhMin, " +
                "tur_TotWhMax " +
                "FROM wtur_10min_report " +
                    "WHERE group_id = $1 " +
                    "AND device_id = $2 " +
                    "AND measure_date >= $3 AND measure_date < $4 " +
                    "ORDER BY measure_date ASC";

        return r2dbcEntityTemplate.getDatabaseClient()
                .sql(sql)
                .bind(0, 0)
                .bind(1, 0)
                .bind(2, startDate)
                .bind(3, endDate)
                .fetch()
                .all()
                .map(row -> new ReportData(
                        (String) row.get("measure_date"),
                        (String) row.get("st_fullPerTm"),
                        (String) row.get("st_parPerTm"),
                        (String) row.get("st_tecStdbyTm"),
                        (String) row.get("st_outEnvTm"),
                        (String) row.get("st_reqShtdnTm"),
                        (String) row.get("st_outElecSpTm"),
                        (String) row.get("st_schMtncTm"),
                        (String) row.get("rot_RotSpd"),
                        (String) row.get("nac_WdSpdNac1sNTFAvg"),
                        (String) row.get("nac_NacOutTmp"),
                        String.valueOf(Double.parseDouble((String)row.get("tur_TotWhMax")) - Double.parseDouble((String)row.get("tur_TotWhMin")))
                ))
                .collectList();
    }
}
