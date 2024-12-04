package com.unison.monitoring.report.service;

import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.alarm.dto.AlarmDto;
import com.unison.monitoring.alarm.service.AlarmServiceDomainImpl;
import com.unison.monitoring.archiveddata.dto.ArchivedDataDto;
import com.unison.monitoring.archiveddata.service.ArchivedDataService;
import com.unison.monitoring.common.dto.TimeChart;
import com.unison.monitoring.common.exception.runtime.InvalidDateTimeException;
import com.unison.monitoring.data.dto.DataDto;
import com.unison.monitoring.data.service.DataService;
import com.unison.monitoring.generaloverview.dto.GeneralOverviewDto;
import com.unison.monitoring.generaloverview.service.GeneralOverviewService;
import com.unison.monitoring.powercurve.dto.PowerCurveDto;
import com.unison.monitoring.powercurve.service.ReferencePowerCurveService;
import com.unison.monitoring.remarks.dto.RemarkDto;
import com.unison.monitoring.remarks.service.RemarkService;
import com.unison.monitoring.report.dto.DailyReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements DailyReportGenerateService{

    private final ArchivedDataService archivedDataService;
    private final GeneralOverviewService generalOverviewService;
    private final DataService dataService;
    private final ReferencePowerCurveService referencePowerCurveService;
    private final AlarmServiceDomainImpl alarmServiceDomainImpl;
    private final RemarkService remarkService;

    @Override
    public DailyReportDto generateDailyReport(UUID turbineUuid, LocalDateTime writeDate) {
        if(writeDate.getHour() != 0 || writeDate.getMinute() != 0 || writeDate.getSecond() != 0){
            throw new InvalidDateTimeException(writeDate);
        }
        DailyReportDto result;

        DailyReportSummary dailyReportSummary = getDailyReportSummary(turbineUuid, writeDate);

        result = DailyReportDto.builder()
                .date(writeDate)
                .operatingPeriod(dailyReportSummary.getOperatingPeriod())
                .writtenDate(LocalDateTime.now())
                .windSpeed(dailyReportSummary.getWindSpeed())
                .activePower(dailyReportSummary.getActivePower())
                .operatingTime(dailyReportSummary.getOperatingTime())
                .generatingTime(dailyReportSummary.getGeneratingTime())
                .startDate(dailyReportSummary.getCommissionDate())
                .totalActivePower(dailyReportSummary.getTotalActivePower())
                .totalOperatingTime(dailyReportSummary.getTotalOperatingTime())
                .totalGeneratingTime(dailyReportSummary.getTotalGeneratingTime())
                .ratedPower(dailyReportSummary.getRatedPower())
                .referencePowerCurve(getReferencePowerCurve(turbineUuid))
                .powerCurveScatter(getPowerCurve(turbineUuid, writeDate, writeDate.plusDays(1)))
                .timeChart(getTimeChart(turbineUuid, writeDate, writeDate.plusDays(1)))
                .alarms(getAlarms(turbineUuid, writeDate, writeDate.plusDays(1)))
                .eventBoxNotes(getRemarks(turbineUuid, writeDate, writeDate.plusDays(1)))
                .build();

        return result;
    }

    private DailyReportSummary getDailyReportSummary(UUID turbineUuid, LocalDateTime writeDate) {
        ArchivedDataDto archivedDataDto = archivedDataService.getArchivedDataByUUID(turbineUuid);

        String operatingPeriod = DateTimeUtils.formatLocalDateTime("`yy.MM.dd 00:00 ~ 24:00", writeDate);

        GeneralOverviewDto generalOverviewDto = generalOverviewService.findByTurbineUuid(turbineUuid);

        LocalDateTime commissionStartDate = generalOverviewDto.getCommissionDate();

        List<DataDto> totalDataDtoList = dataService.findByTurbineUuidAndTime(turbineUuid, commissionStartDate, writeDate.plusDays(1));


        return DailyReportSummary.builder()
                .commissionDate(commissionStartDate)
                .writtenDate(writeDate)
                .operatingPeriod(operatingPeriod)
                .windSpeed(getWindSpeedAverage(writeDate, totalDataDtoList))
                .activePower(getActivePowerAverage(writeDate, totalDataDtoList))
                .operatingTime(getOperatingTime(writeDate, totalDataDtoList))
                .generatingTime(getGeneratingTime(writeDate, totalDataDtoList))
                .totalActivePower(archivedDataDto.getActivePower() + getActivePowerAverage(totalDataDtoList))
                .totalOperatingTime(calculateOperatingTime(archivedDataDto) + getOperatingTime(totalDataDtoList))
                .totalGeneratingTime(calculateGeneratingTime(archivedDataDto) + getGeneratingTime(totalDataDtoList))
                .ratedPower(generalOverviewDto.getRatedPower())
                .build();
    }

    private double getWindSpeedAverage(LocalDateTime targetDate, List<DataDto> dataDtoList){
        if (dataDtoList.isEmpty()) {
            return 0.0;
        }

        if(targetDate == null){
            return dataDtoList.stream()
                    .mapToDouble(DataDto::getWindSpeed)
                    .average()
                    .orElse(0.0);
        }
        else {
            return dataDtoList.stream()
                    .filter(dataDto ->
                            dataDto.getTimestamp().getYear() == targetDate.getYear()
                                    && dataDto.getTimestamp().getMonth() == targetDate.getMonth()
                                    && dataDto.getTimestamp().getDayOfMonth() == targetDate.getDayOfMonth()
                    )
                    .mapToDouble(DataDto::getWindSpeed)
                    .average()
                    .orElse(0.0);
        }
    }
    private double getWindSpeedAverage(List<DataDto> dataDtoList) {
        return getWindSpeedAverage(null, dataDtoList);
    }

    private double getActivePowerAverage(LocalDateTime targetDate, List<DataDto> dataDtoList){
        if (dataDtoList.isEmpty()) {
            return 0.0;
        }
        double result;

        if(targetDate == null){
            Map<String, Double> dataDtoMap = dataDtoList.stream()
                    .collect(
                            Collectors.groupingBy(
                                    (dataDto) -> DateTimeUtils.formatLocalDateTime("yyyy-MM-dd", dataDto.getTimestamp()),
                                    Collectors.averagingDouble(DataDto::getActivePower)
                            )
                    );
            result = dataDtoMap.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

        }
        else {
            result = dataDtoList.stream()
                    .filter(dataDto ->
                            dataDto.getTimestamp().getYear() == targetDate.getYear()
                                    && dataDto.getTimestamp().getMonth() == targetDate.getMonth()
                                    && dataDto.getTimestamp().getDayOfMonth() == targetDate.getDayOfMonth()
                    )
                    .mapToDouble(DataDto::getActivePower)
                    .average()
                    .orElse(0.0);

        }
        return result * 24;
    }

    private double getActivePowerAverage(List<DataDto> dataDtoList){
        return getActivePowerAverage(null, dataDtoList);
    }

    private int getOperatingTime(LocalDateTime targetDate, List<DataDto> dataDtoList){
        if (dataDtoList.isEmpty()) {
            return 0;
        }

        if(targetDate == null){
            return dataDtoList.stream()
                    .mapToInt(this::calculateOperatingTime)
                    .sum();
        }
        else {
            return dataDtoList.stream()
                    .filter(dataDto ->
                            dataDto.getTimestamp().getYear() == targetDate.getYear()
                                    && dataDto.getTimestamp().getMonth() == targetDate.getMonth()
                                    && dataDto.getTimestamp().getDayOfMonth() == targetDate.getDayOfMonth()
                    )
                    .mapToInt(this::calculateOperatingTime)
                    .sum();
        }
    }

    private int getOperatingTime(List<DataDto> dataDtoList){
        return getOperatingTime(null, dataDtoList);
    }

    private int getGeneratingTime(LocalDateTime targetDate, List<DataDto> dataDtoList){
        if (dataDtoList.isEmpty()) {
            return 0;
        }

        if(targetDate == null){
            return dataDtoList.stream()
                    .mapToInt(this::calculateGeneratingTime)
                    .sum();
        }
        else {
            return dataDtoList.stream()
                    .filter(dataDto ->
                            dataDto.getTimestamp().getYear() == targetDate.getYear()
                                    && dataDto.getTimestamp().getMonth() == targetDate.getMonth()
                                    && dataDto.getTimestamp().getDayOfMonth() == targetDate.getDayOfMonth()
                    )
                    .mapToInt(this::calculateGeneratingTime)
                    .sum();
        }
    }

    private int getGeneratingTime(List<DataDto> dataDtoList){
        return getGeneratingTime(null, dataDtoList);
    }

    private List<PowerCurveDto> getReferencePowerCurve(UUID turbineUuid){
        List<PowerCurveDto> referencePowerCurve = referencePowerCurveService.getReferencePowerCurve(turbineUuid);

        return referencePowerCurve.stream().map((data) -> new PowerCurveDto(
                data.getTurbineUuid(),
                String.format("%.02f", Double.parseDouble(data.getWindSpeed())),
                String.format("%.02f", Double.parseDouble(data.getActivePower()))
        )).collect(Collectors.toList());

    }

    private List<PowerCurveDto> getPowerCurve(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate){
        LocalDateTime firstQuarter = LocalDateTime.of(startDate.getYear(), startDate.getMonth().firstMonthOfQuarter() , 1, 0, 0);

        List<PowerCurveDto> powerCurve = dataService.getPowerCurveByTurbineUuidAndTime(turbineUuid, firstQuarter, endDate);

        return powerCurve.stream().map((data) -> new PowerCurveDto(
                data.getTurbineUuid(),
                String.format("%.02f", Double.parseDouble(data.getWindSpeed())),
                String.format("%.02f", Double.parseDouble(data.getActivePower()))
        )).collect(Collectors.toList());
    }

    private List<TimeChart> getTimeChart(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate){

        List<TimeChart> formattedTimeChart = dataService.getTimeChart(turbineUuid, startDate, endDate);

        return formattedTimeChart.stream().map((data) -> new TimeChart(
                data.getTimestamp(),
                String.format("%.02f", Double.parseDouble(data.getWindSpeed())),
                String.format("%.02f", Double.parseDouble(data.getActivePower())),
                String.format("%.02f", Double.parseDouble(data.getRotorSpeed()))
        )).collect(Collectors.toList());
    }

    private List<AlarmDto> getAlarms(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime) {

        return alarmServiceDomainImpl.getAlarmsAbove(turbineUuid, startTime, endTime, 20000);
    }

    private List<RemarkDto> getRemarks(UUID turbineUuid, LocalDateTime startTime, LocalDateTime endTime) {
        return remarkService.getRemarks(turbineUuid, startTime, endTime);
    }

    private int calculateOperatingTime(DataDto dto) {
        return sumOfFields(dto.getFullPerformance(), dto.getPartialPerformance(),
                dto.getOutOfElectrical(), dto.getOutOfEnvironment(),
                dto.getRequestedShutdown(), dto.getScheduledMaintenance(),
                dto.getTechnicalStandby());
    }

    private int calculateGeneratingTime(DataDto dto) {
        return sumOfFields(dto.getFullPerformance(), dto.getPartialPerformance());
    }
    private int calculateOperatingTime(ArchivedDataDto dto) {
        return sumOfFields(dto.getFullPerformance(), dto.getPartialPerformance(),
                dto.getOutOfElectrical(), dto.getOutOfEnvironment(),
                dto.getRequestedShutdown(), dto.getScheduledMaintenance(),
                dto.getTechnicalStandby());
    }

    private int calculateGeneratingTime(ArchivedDataDto dto) {
        return sumOfFields(dto.getFullPerformance(), dto.getPartialPerformance());
    }


    private int sumOfFields(Integer... fields) {
        int sum = 0;
        for (Integer field : fields) {
            if (field != null) sum += field;
        }
        return sum;
    }
}

