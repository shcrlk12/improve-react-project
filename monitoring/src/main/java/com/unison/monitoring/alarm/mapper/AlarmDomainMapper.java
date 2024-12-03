package com.unison.monitoring.alarm.mapper;

import com.unison.monitoring.alarm.dto.AlarmDto;
import com.unison.monitoring.alarm.entity.AlarmEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AlarmMapper {

    public static AlarmDto convertAlarmDto(AlarmEntity alarmEntity){

        return new AlarmDto(
                alarmEntity.getId().getTimestamp(),
                alarmEntity.getId().getAlarmNumber(),
                alarmEntity.getId().getTurbineUuid(),
                alarmEntity.getAlarmCode(),
                alarmEntity.getComment(),
                alarmEntity.getRemark());
    }

    public static List<AlarmDto> convertAlarmDtos(List<AlarmEntity> alarmEntities){

        return alarmEntities.stream()
                .map(AlarmMapper::convertAlarmDto)
                .collect(Collectors.toList());
    }

    public static AlarmEntity convertAlarmEntity(AlarmDto alarmDto){

        return new AlarmEntity(
                new AlarmEntity.Id(
                        alarmDto.getTimestamp(), alarmDto.getAlarmNumber(), alarmDto.getTurbineUuid()
                ),
                        alarmDto.getAlarmCode(),
                        alarmDto.getComment(),
                        alarmDto.getRemark()
                );
    }

    public static List<AlarmEntity> convertAlarmEntities(List<AlarmDto> alarmDto){

        return alarmDto.stream()
                .map(AlarmMapper::convertAlarmEntity)
                .collect(Collectors.toList());
    }

    public static AlarmEntity convertAlarmEntity(com.unison.common.dto.AlarmDto.Response alarmDtoResponse){

        return new AlarmEntity(
                new AlarmEntity.Id(
                        alarmDto.getTimestamp(), alarmDto.getAlarmNumber(), alarmDto.getTurbineUuid()
                ),
                        alarmDto.getAlarmCode(),
                        alarmDto.getComment(),
                        alarmDto.getRemark()
                );
    }
}
