package com.unison.monitoring.alarm.mapper;

import com.unison.monitoring.alarm.dto.AlarmDto;
import com.unison.monitoring.alarm.entity.AlarmEntity;
import com.unison.monitoring.report.dto.ReportDto;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlarmDomainMapper {

    public static AlarmDto convertAlarmDto(AlarmEntity alarmEntity){

        return new AlarmDto(
                alarmEntity.getId().getTimestamp(),
                alarmEntity.getId().getAlarmNumber(),
                alarmEntity.getId().getTurbineUuid(),
                null,
                alarmEntity.getAlarmCode(),
                alarmEntity.getComment(),
                alarmEntity.getRemark());
    }

    public static List<AlarmDto> convertAlarmDtos(List<AlarmEntity> alarmEntities){

        return alarmEntities.stream()
                .map(AlarmDomainMapper::convertAlarmDto)
                .collect(Collectors.toList());
    }

    public static List<AlarmDto> convertAlarmDtoList(List<AlarmEntity> alarmEntities, Map<String, String> alarmMap, int threshold){
        List<AlarmDto> result = null;

        try{
            result = alarmEntities.stream()
                    .filter(alarmEntity ->
                            Optional.ofNullable(alarmEntity.getId().getAlarmNumber())
                                    .map(Integer::parseInt)
                                    .orElse(0) > threshold
                    )
                    .map(alarmEntity -> new AlarmDto(
                            alarmEntity.getId().getTimestamp()
                            , alarmEntity.getId().getAlarmNumber()
                            , alarmEntity.getId().getTurbineUuid()
                            , alarmMap.get(alarmEntity.getAlarmCode())
                            , alarmEntity.getAlarmCode()
                            , alarmEntity.getComment()
                            , Optional.ofNullable(alarmEntity.getRemark()).orElse(""))
                    )
                    .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
        }

        return Objects.requireNonNull(result);
    }

    public static AlarmEntity convertAlarmEntity(AlarmDto alarmDto){

        return new AlarmEntity(
                new AlarmEntity.Id(
                        alarmDto.getTimestamp(), alarmDto.getAlarmNumber(), alarmDto.getTurbineUuid()
                ),
                        alarmDto.getAlarmCode(),
                        alarmDto.getComment(),
                        alarmDto.getRemarks()
                );
    }

    public static List<AlarmEntity> convertAlarmEntities(List<AlarmDto> alarmDtos){

        return alarmDtos.stream()
                .map(AlarmDomainMapper::convertAlarmEntity)
                .collect(Collectors.toList());
    }
}
