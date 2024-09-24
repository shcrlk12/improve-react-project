package com.unison.batch.service;

import com.unison.common.domain.ReportData;
import com.unison.common.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class U113UploadBatchService implements UploadBatchService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ReportData> getReportData(LocalDateTime startDate, LocalDateTime endDate) {
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

        return jdbcTemplate.query(
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
        );
    }
}
