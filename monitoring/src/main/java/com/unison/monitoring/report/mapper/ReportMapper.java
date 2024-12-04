package com.unison.monitoring.report.mapper;

import com.unison.monitoring.report.dto.DailyReportDto;
import com.unison.monitoring.report.dto.ReportDto;

public class ReportMapper {

    public static ReportDto.Response toReportDtoResponse(DailyReportDto dailyReportDto){

        return ReportDto.Response.builder()
                .date(dailyReportDto.getDate())
                .operatingPeriod(dailyReportDto.getOperatingPeriod())
                .writtenDate(dailyReportDto.getWrittenDate())
                .windSpeed(dailyReportDto.getWindSpeed())
                .activePower(dailyReportDto.getActivePower())
                .operatingTime(dailyReportDto.getOperatingTime())
                .generatingTime(dailyReportDto.getGeneratingTime())
                .ratedPower(dailyReportDto.getRatedPower())
                .startDate(dailyReportDto.getStartDate())
                .totalActivePower(dailyReportDto.getTotalActivePower())
                .totalOperatingTime(dailyReportDto.getTotalOperatingTime())
                .totalGeneratingTime(dailyReportDto.getTotalGeneratingTime())
                .referencePowerCurve(dailyReportDto.getReferencePowerCurve())
                .powerCurveScatter(dailyReportDto.getPowerCurveScatter())
                .timeChart(dailyReportDto.getTimeChart())
                .alarms(dailyReportDto.getAlarms())
                .eventBoxNotes(dailyReportDto.getEventBoxNotes())
                .build();

    }
}
