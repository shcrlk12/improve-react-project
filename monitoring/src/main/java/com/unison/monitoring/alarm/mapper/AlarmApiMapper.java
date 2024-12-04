package com.unison.monitoring.alarm.mapper;

import com.unison.common.dto.AlarmDto;
import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.alarm.entity.AlarmEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AlarmApiMapper {
    public static AlarmDto.Response toAlarmResponseDto(AlarmEntity alarmEntity){

        return new AlarmDto.Response(
                alarmEntity.getId().getAlarmNumber(),
                alarmEntity.getAlarmCode(),
                alarmEntity.getComment(),
                alarmEntity.getId().getTimestamp().toString()
        );
    }

    public static List<AlarmDto.Response> toAlarmResponseDtoList(List<AlarmEntity> alarmEntities){

        return alarmEntities.stream()
                .map(AlarmApiMapper::toAlarmResponseDto)
                .collect(Collectors.toList());
    }

    public static AlarmEntity toEntity(AlarmDto.Response alarmDto, UUID turbineUuid){

        return new AlarmEntity(
                new AlarmEntity.Id(
                        DateTimeUtils.parseLocalDateTime(alarmDto.getAlarmLogTimestamp()), alarmDto.getAlarmNumber(), turbineUuid
                ),
                alarmDto.getAlarmCode(),
                alarmDto.getComment(),
                null
        );
    }

    public static List<AlarmEntity> toEntities(List<AlarmDto.Response> alarmDtos, UUID turbineUuid){

        return alarmDtos.stream()
                .map(alarmDto -> toEntity(alarmDto, turbineUuid))
                .collect(Collectors.toList());
    }
}
