package com.unison.monitoring.alarm.service;

import com.unison.monitoring.alarm.dto.AlarmDto;
import com.unison.monitoring.alarm.entity.AlarmEntity;
import com.unison.monitoring.alarm.mapper.AlarmMapper;
import com.unison.monitoring.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService<AlarmDto>{
    private final AlarmRepository alarmRepository;
    @Override
    public List<AlarmDto> findAlarmsByTurbineUuidAndBetweenTime(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime) {
        List<AlarmEntity> alarmEntities = alarmRepository.findByTurbineUuidAndTimestamp(turbineUuid, startTime, endTime);

        return AlarmMapper.convertAlarmDtos(alarmEntities);
    }

    @Override
    public void saveAlarm(AlarmDto alarmDto) {
        AlarmEntity alarmEntity = AlarmMapper.convertAlarmEntity(alarmDto);

        alarmRepository.save(alarmEntity);
    }

    @Override
    public void saveAlarm(com.unison.common.dto.AlarmDto.Response alarmDto) {
        AlarmEntity alarmEntity = AlarmMapper.convertAlarmEntity(alarmDto);

        alarmRepository.save(alarmEntity);
    }
    @Override
    public void saveAlarms(List<AlarmDto> alarmDtos) {
        List<AlarmEntity> alarmEntities = AlarmMapper.convertAlarmEntities(alarmDtos);

        alarmRepository.saveAll(alarmEntities);
    }
}

