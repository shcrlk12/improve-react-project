package com.unison.common.dto;

import lombok.*;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class AlarmDto {
    public static String TYPE = "alarms";

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String alarmNumber;
        private String alarmCode;
        private String comment;
        private String alarmLogTimestamp;
    }

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String alarmNumber;
        private String alarmCode;
        private String comment;
        private String alarmLogTimestamp;
    }
}
