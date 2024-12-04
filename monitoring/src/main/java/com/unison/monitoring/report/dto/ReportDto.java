package com.unison.monitoring.report.dto;

import com.unison.monitoring.alarm.dto.AlarmDto;
import com.unison.monitoring.common.dto.TimeChart;
import com.unison.monitoring.powercurve.dto.PowerCurveDto;
import com.unison.monitoring.remarks.dto.RemarkDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReportDto {
    public final static String TYPE = "report-data";

    @Setter
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private LocalDateTime date;
        private String operatingPeriod;
        private LocalDateTime writtenDate;
        private Double windSpeed;
        private Double activePower;
        private Integer operatingTime; // seconds
        private Integer generatingTime;
        private Integer ratedPower;
        private LocalDateTime startDate;
        private Double totalActivePower;
        private Integer totalOperatingTime;
        private Integer totalGeneratingTime;

        private List<PowerCurveDto> referencePowerCurve;
        private List<PowerCurveDto> powerCurveScatter;
        private List<TimeChart> timeChart;
        private List<AlarmDto> alarms;
        private List<RemarkDto> eventBoxNotes;
    }
}
