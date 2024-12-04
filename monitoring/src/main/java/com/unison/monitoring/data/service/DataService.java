package com.unison.monitoring.data.service;

import com.unison.monitoring.common.dto.TimeChart;
import com.unison.monitoring.data.dto.DataDto;
import com.unison.monitoring.powercurve.dto.PowerCurveDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DataService {


    List<TimeChart> getTimeChart(UUID uuid, LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    List<PowerCurveDto> getPowerCurveByTurbineUuidAndTime(UUID uuid, LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    List<DataDto> findByTurbineUuidAndTime(UUID uuid, LocalDateTime startTimestamp, LocalDateTime endTimestamp);
}
