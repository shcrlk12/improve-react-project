package com.unison.monitoring.alarm.service;

import com.unison.common.dto.AlarmDto;
import com.unison.monitoring.alarm.entity.AlarmEntity;
import com.unison.monitoring.alarm.mapper.AlarmApiMapper;
import com.unison.monitoring.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AlarmServiceApiImpl implements AlarmService<AlarmDto.Response, AlarmDto.Response>{
    private final AlarmRepository alarmRepository;

    @Override
    public List<AlarmDto.Response> findAlarmsByTurbineUuidAndBetweenTime(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime) {
        List<AlarmEntity> alarmEntities = alarmRepository.findByTurbineUuidAndTimestamp(turbineUuid, startTime, endTime);

        return AlarmApiMapper.toAlarmResponseDtoList(alarmEntities);
    }

    @Override
    public List<AlarmDto.Response> getAlarmsAbove(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime, int threshold) {
        return null;
    }

    @Override
    public void saveAlarm(UUID turbineUuid, AlarmDto.Response alarmDto) {
        AlarmEntity alarmEntity = AlarmApiMapper.toEntity(alarmDto, turbineUuid);

        alarmRepository.save(alarmEntity);
    }

    @Override
    public void saveAlarms(UUID turbineUuid, List<AlarmDto.Response> alarmDtos) {
        List<AlarmEntity> alarmEntities = AlarmApiMapper.toEntities(alarmDtos, turbineUuid);

        alarmRepository.saveAll(alarmEntities);
    }
}
