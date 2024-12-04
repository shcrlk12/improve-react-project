package com.unison.monitoring.alarm.service;

import com.unison.monitoring.alarm.dto.AlarmDto;
import com.unison.monitoring.alarm.entity.AlarmEntity;
import com.unison.monitoring.alarm.mapper.AlarmDomainMapper;
import com.unison.monitoring.alarm.repository.AlarmRepository;
import com.unison.monitoring.common.config.AlarmMapRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlarmServiceDomainImpl implements AlarmService<AlarmDto, AlarmDto>{
    private final AlarmRepository alarmRepository;
    private final AlarmMapRegistry alarmMapRegistry;

    @Override
    public List<AlarmDto> findAlarmsByTurbineUuidAndBetweenTime(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime) {
        List<AlarmEntity> alarmEntities = alarmRepository.findByTurbineUuidAndTimestamp(turbineUuid, startTime, endTime);

        return AlarmDomainMapper.convertAlarmDtos(alarmEntities);
    }

    @Override
    public List<AlarmDto> getAlarmsAbove(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime, int threshold) {
        List<AlarmEntity> alarmEntities = alarmRepository.findByTurbineUuidAndTimestamp(turbineUuid, startTime, endTime);

        Map<String, String> alarmsMap = alarmMapRegistry.getAlarmMap(turbineUuid);

        return AlarmDomainMapper.convertAlarmDtoList(alarmEntities, alarmsMap, threshold);
    }


    @Override
    public void saveAlarm(UUID turbineUuid, AlarmDto alarmDto) {
        AlarmEntity alarmEntity = AlarmDomainMapper.convertAlarmEntity(alarmDto);

        alarmRepository.save(alarmEntity);
    }

    @Override
    public void saveAlarms(UUID turbineUuid, List<AlarmDto> alarmDtos) {
        List<AlarmEntity> alarmEntities = AlarmDomainMapper.convertAlarmEntities(alarmDtos);

        alarmRepository.saveAll(alarmEntities);
    }
}

