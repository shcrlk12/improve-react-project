package com.unison.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Alarm {
    private final String alarmId;
    private final String alarmNumber;
    private final String alarmCode;
    private final String comment;
    private final String alarmLogTimestamp;
}
