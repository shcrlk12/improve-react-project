package com.unison.monitoring.data.service;

import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.alarm.service.AlarmService;
import com.unison.monitoring.common.dto.TimeChart;
import com.unison.monitoring.common.exception.runtime.LocalDateTimeNullPointException;
import com.unison.monitoring.common.exception.runtime.TurbineUuidNotFoundException;
import com.unison.monitoring.data.dto.DataDto;
import com.unison.monitoring.data.entity.DataEntity;
import com.unison.monitoring.data.mapper.DataMapper;
import com.unison.monitoring.data.repository.DataRepository;
import com.unison.monitoring.generaloverview.entity.GeneralOverviewEntity;
import com.unison.monitoring.generaloverview.repository.GeneralOverviewRepository;
import com.unison.monitoring.powercurve.dto.PowerCurveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService, DataUploadService{
    private final DataRepository dataRepository;
    private final GeneralOverviewRepository generalOverviewRepository;
    private final AlarmService<AlarmDto.Response, AlarmDto.Response> alarmServiceApiImpl;

    @Override
    public List<TimeChart> getTimeChart(UUID uuid, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        if(startTimestamp == null){
            throw new LocalDateTimeNullPointException("");
        }

        LocalDateTime startDate = startTimestamp;
        LocalDateTime endDate;

        if(endTimestamp == null)
            endDate = startTimestamp.plusDays(1);
        else  {
            endDate = endTimestamp;
        }

        List<DataEntity> dataEntities = dataRepository.findByUuidAndTimestamp(uuid, startDate, endDate);


        Duration duration = Duration.between(startTimestamp, endTimestamp);

        long pointNumber = duration.toHours() * 6;

        if(dataEntities.size() < pointNumber){
            Set<LocalDateTime> existingTimestamps = dataEntities.stream()
                    .map(data -> data.getId().getTimestamp())
                    .collect(Collectors.toSet());

            for (LocalDateTime tempDate = startDate; tempDate.compareTo(endDate) <= 0; tempDate = tempDate.plusMinutes(10)){
                if (!existingTimestamps.contains(tempDate)) {

                    dataEntities.add(DataEntity.builder()
                            .id(DataEntity.Id.builder()
                                    .timestamp(tempDate)
                                    .build())
                            .rotorSpeed(0.0)
                            .activePower(0.0)
                            .windSpeed(0.0)
                            .nacOutTmp(0.0)
                            .build());
                    existingTimestamps.add(tempDate);
                }
            }
        }

        dataEntities.sort(new Comparator<>() {
            @Override
            public int compare(DataEntity o1, DataEntity o2) {
                return o1.getId().getTimestamp().compareTo(o2.getId().getTimestamp());
            }
        });

        return DataMapper.toTimeChart(dataEntities);
    }

    @Override
    public List<PowerCurveDto> getPowerCurveByTurbineUuidAndTime(UUID uuid, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        List<DataEntity> dataEntities = dataRepository.findByUuidAndTimestamp(uuid, startTimestamp, endTimestamp);


        return DataMapper.toPowerCurveDto(dataEntities);
    }

    @Override
    public List<DataDto> findByTurbineUuidAndTime(UUID uuid, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {

        return DataMapper.toDtoList(dataRepository.findByUuidAndTimestamp(uuid, startTimestamp, endTimestamp));
    }

    @Override
    public void uploadData(List<ReportData> reportDataList, UUID turbineUuid) {
        List<DataEntity> dataEntityList = new ArrayList<>();

        GeneralOverviewEntity optionalGeneralOverview = generalOverviewRepository.findById(turbineUuid)
                .orElseThrow(() -> new TurbineUuidNotFoundException("general overview", turbineUuid));

        for (ReportData reportData : reportDataList) {
            dataEntityList.add(
                    DataEntity.builder()
                            .id(DataEntity.Id.builder()
                                    .timestamp(DateTimeUtils.parseISOLocalDateTime(reportData.getMeasureDate()))
                                    .generalOverviewEntity(optionalGeneralOverview)
                                    .build())
                            .fullPerformance(Integer.parseInt(reportData.getFullPerformance()))
                            .partialPerformance(Integer.parseInt(reportData.getPartialPerformance()))
                            .outOfElectrical(Integer.parseInt(reportData.getOutOfElectrical()))
                            .outOfEnvironment(Integer.parseInt(reportData.getOutOfEnvironment()))
                            .requestedShutdown(Integer.parseInt(reportData.getRequestedShutdown()))
                            .scheduledMaintenance(Integer.parseInt(reportData.getScheduledMaintenance()))
                            .technicalStandby(Integer.parseInt(reportData.getTechnicalStandby()))
                            .rotorSpeed(Double.parseDouble(reportData.getRotorSpeed()))
                            .windSpeed(Double.parseDouble(reportData.getWindSpeed()))
                            .nacOutTmp(Double.parseDouble(reportData.getNacOutTmp()))
                            .activePower(Double.parseDouble(reportData.getActivePower()))
                            .build()
            );
        }

        dataRepository.saveAll(dataEntityList);
    }

    @Override
    public void uploadAlarms(List<AlarmDto.Response> alarmList, UUID turbineUuid) throws Exception {
        if(alarmList == null || alarmList.isEmpty()){
            return;
        }

        if(generalOverviewRepository.findById(turbineUuid).isPresent()) {

            alarmServiceApiImpl.saveAlarms(turbineUuid, alarmList);
        }
    }
}
