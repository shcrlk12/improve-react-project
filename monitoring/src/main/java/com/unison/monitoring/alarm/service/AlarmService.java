package com.unison.monitoring.alarm.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AlarmService<RS, RQ> {
    List<RS> findAlarmsByTurbineUuidAndBetweenTime(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime);

    List<RS> getAlarmsAbove(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime, int threshold);

    void saveAlarm(UUID turbineUuid, RQ alarmDto);

    void saveAlarms(UUID turbineUuid, List<RQ> alarmDtos);
}