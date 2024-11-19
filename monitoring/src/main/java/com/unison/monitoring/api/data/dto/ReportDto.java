package com.unison.monitoring.api.data.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

        private LocalDateTime startDate;
        private Double totalActivePower;
        private Integer totalOperatingTime;
        private Integer totalGeneratingTime;

        private List<PowerCurve> referencePowerCurve;
        private List<PowerCurve> powerCurveScatter;
        private List<TimeChart> timeChart;
        private List<Alarm> alarms;
        private List<Remark> eventBoxNotes;
    }

    @Getter
    @AllArgsConstructor
    public static class PowerCurve{
        private Double windSpeed;
        private Double activePower;
    }

    @Getter
    @AllArgsConstructor
    public static class TimeChart{
        private LocalDateTime timestamp;
        private Double rotorSpeed;
        private Double windSpeed;
        private Double activePower;
    }

    @Getter
    @AllArgsConstructor
    public static class Alarm {
        private LocalDateTime timestamp;
        private Integer alarmCode;
        private String alarmName;
        private String remarks;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Remark{
        private String title;
        private String content;
        private UUID uuid;
    }
}
