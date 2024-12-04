package com.unison.monitoring.alarm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlarmDto {
    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    @JsonProperty("alarmNumber")
    private final String alarmNumber;

    @JsonProperty("turbineUuid")
    private final UUID turbineUuid;

    @JsonProperty("alarmName")
    private final String alarmName;

    @JsonProperty("alarmCode")
    private final String alarmCode;

    @JsonProperty("comment")
    private final String comment;

    @JsonProperty("remarks")
    private final String remarks;
}
