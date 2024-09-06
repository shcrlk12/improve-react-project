package com.unison.batch.service;

import com.unison.batch.dto.LastUpdateDto;
import com.unison.batch.jsonapi.request.ApiRequests;
import com.unison.batch.jsonapi.response.ApiResponse;
import com.unison.batch.domain.ReportData;
import com.unison.common.dto.TimeDto;
import com.unison.common.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class U113UploadBatchService implements UploadBatchService {
    private final JdbcTemplate jdbcTemplate;

    private final WebClient webClient;

    @Override
    public Mono<LastUpdateDto.Response> retrieveLastUploadDate() {

        return webClient.get()
                .uri("/api/data/last-update")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<TimeDto.Response>>() {})
                .map(responseEntity ->
                        LastUpdateDto.Response.builder()
                                .uuid(UUID.fromString(responseEntity.getBody().getData().getId()))
                                .lastUpdateTime(DateTimeUtils.parseISOLocalDateTime(responseEntity.getBody().getData().getAttributes().getTime()))
                                .build()
                );
    }

    @Override
    public void uploadData(ApiRequests<?> request) {
        webClient.post()
                .uri("/api/data")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();  // 비동기 방식으로 처리
    }

    @Override
    public Mono<List<ReportData>> getReportData(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT " +
                "measure_date, " +
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
                "WHERE group_id = ? " +
                "AND device_id = ? " +
                "AND measure_date >= ? AND measure_date < ? " +
                "ORDER BY measure_date ASC";

        return Mono.just(jdbcTemplate.query(
                sql,
                ps -> {
                    ps.setString(1 , "1");
                    ps.setString(2 , "1");
                    ps.setString(3 , DateTimeUtils.formatLocalDateTime( "yyyy-MM-dd HH:mm:ss", startDate));
                    ps.setString(4 , DateTimeUtils.formatLocalDateTime( "yyyy-MM-dd HH:mm:ss", endDate));

                },
                (ResultSet rs, int rowNum)  ->
                    new ReportData(
                            rs.getString("measure_date"),
                            rs.getString("st_fullPerTm"),
                            rs.getString("st_parPerTm"),
                            rs.getString("st_tecStdbyTm"),
                            rs.getString("st_outEnvTm"),
                            rs.getString("st_reqShtdnTm"),
                            rs.getString("st_outElecSpTm"),
                            rs.getString("st_schMtncTm"),
                            rs.getString("rot_RotSpd"),
                            rs.getString("nac_WdSpdNac1sNTFAvg"),
                            rs.getString("nac_NacOutTmp"),
                            String.valueOf(Double.parseDouble(rs.getString("tur_TotWhMax")) - Double.parseDouble(rs.getString("tur_TotWhMin")))
                    )
        ));
    }
}
