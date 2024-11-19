package com.unison.monitoring.api;

import com.unison.common.domain.Remark;
import com.unison.common.dto.DailyReportRemarkDto;
import com.unison.common.dto.RemarkDto;
import com.unison.common.dto.SitesDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.monitoring.api.data.dto.ReportDto;
import com.unison.monitoring.api.entity.*;

import java.util.*;
import java.util.stream.Collectors;

public class DataMapper {

    public static List<ReportDto.PowerCurve> convertToReportDtoReferencePowerCurve(List<PowerCurveEntity> powerCurveEntities){

        return powerCurveEntities.stream()
                .map(powerCurveEntity -> new ReportDto.PowerCurve(powerCurveEntity.getWindSpeed(), powerCurveEntity.getActivePower()))
                .collect(Collectors.toList());
    }

    public static List<ReportDto.PowerCurve> convertToReportDtoPowerCurveScatters(List<DataEntity> dataEntities){

        return dataEntities.stream()
                .filter(dataEntity -> dataEntity.getFullPerformance() == 600)
                .map(dataEntity -> new ReportDto.PowerCurve(dataEntity.getWindSpeed(), dataEntity.getActivePower()))
                .collect(Collectors.toList());

    }

    public static List<ReportDto.TimeChart> convertToReportDtoTimeChartGroups(List<DataEntity> dataEntities){

        return dataEntities.stream()
                .map(dataEntity -> new ReportDto.TimeChart(dataEntity.getId().getTimestamp(), dataEntity.getRotorSpeed(), dataEntity.getWindSpeed(), dataEntity.getActivePower()))
                .collect(Collectors.toList());

    }

    public static List<ReportDto.Alarm> convertToReportDtoAlarmsGroups(List<AlarmEntity> alarmEntities){
        List<ReportDto.Alarm> result = null;

        try{
            result = alarmEntities.stream()
                    .filter(alarmEntity ->
                            Optional.ofNullable(alarmEntity.getId().getAlarmNumber())
                                    .map(Integer::parseInt)
                                    .orElse(0) > 20000
                    )
                    .map(alarmEntity -> new ReportDto.Alarm(
                            alarmEntity.getId().getTimestamp()
                            , Integer.parseInt(alarmEntity.getId().getAlarmNumber())
                            , Optional.ofNullable(alarmEntity.getAlarmCode()).orElse("")
                            , Optional.ofNullable(alarmEntity.getRemark()).orElse(""))
                    )
                    .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
        }

        return Objects.requireNonNull(result);
    }

    public static List<ReportDto.Remark> convertToReportDtoRemarks(List<RemarkDataEntity> remarkEntities){
        List<ReportDto.Remark> result = null;

        try{
            result = remarkEntities.stream()
                    .sorted(Comparator.comparing(o -> o.getRemarkMetaEntity().getId()))
                    .map(remarkDataEntity -> new ReportDto.Remark(remarkDataEntity.getRemarkMetaEntity().getTitle(), remarkDataEntity.getDescription(), remarkDataEntity.getUuid()))
                    .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }

    public static List<SitesDto.Response> convertToSitesDtoResponse(List<GeneralOverviewEntity> generalOverviewEntities){
        List<SitesDto.Response> result = null;

        try{
            result = generalOverviewEntities.stream()
                    .map(generalOverviewEntity -> new SitesDto.Response(generalOverviewEntity.getUuid(), generalOverviewEntity.getSiteName(), generalOverviewEntity.getRemark(), generalOverviewEntity.getRatedPower()))
                    .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }

    public static List<Resource<RemarkDto.Response>> convertToRemarkDtoResponse(ApiRequests<RemarkDto.Request> remarkDtoRequests){
        List<Resource<RemarkDto.Response>> result = null;

        try{
            result = remarkDtoRequests.getData().stream()
                    .map(remarkDtoRequest ->
                            Resource.<RemarkDto.Response>builder()
                                    .id(remarkDtoRequest.getId())
                                    .type(remarkDtoRequest.getType())
                                    .attributes(new RemarkDto.Response(remarkDtoRequest.getAttributes().getTitle(), remarkDtoRequest.getAttributes().getContent(), remarkDtoRequest.getAttributes().getTurbineUuid()))
                                    .build()
                            )
                    .toList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }

    public static List<Remark> convertToRemarkDomain(List<RemarkDataEntity> remarkDataEntities){
        List<Remark> result = null;

        try{
            result = remarkDataEntities.stream()
                    .map(remarkDataEntity ->
                            Remark.builder()
                                    .title(remarkDataEntity.getRemarkMetaEntity().getTitle())
                                    .order(remarkDataEntity.getRemarkMetaEntity().getId())
                                    .content(remarkDataEntity.getDescription())
                                .build()
                    )
                    .toList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }

    public static Resource<DailyReportRemarkDto.Response> convertToDailyReportRemarkDtoResponse(ApiRequest<DailyReportRemarkDto.Request> dailyReportRemarkDtoRequest){
        Resource<DailyReportRemarkDto.Response> result = null;

        try{
            Resource<DailyReportRemarkDto.Request> data = dailyReportRemarkDtoRequest.getData();

            result = Resource.<DailyReportRemarkDto.Response>builder()
                    .id(data.getId())
                    .type(data.getType())
                    .attributes(DailyReportRemarkDto.Response.builder()
                            .remark(data.getAttributes().getRemark())
                            .build())
                    .build();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }
}
