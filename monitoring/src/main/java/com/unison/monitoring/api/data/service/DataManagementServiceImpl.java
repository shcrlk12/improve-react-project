    package com.unison.monitoring.api.data.service;

import com.unison.common.Constants;
import com.unison.common.domain.ReportData;
import com.unison.common.dto.AlarmDto;
import com.unison.common.dto.DailyReportRemarkDto;
import com.unison.common.dto.RemarkDto;
import com.unison.common.dto.SitesDto;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.api.DailyDataStats;
import com.unison.monitoring.api.DataMapper;
import com.unison.monitoring.api.PowerCurveRepository;
import com.unison.monitoring.api.data.dto.ReportDto;
import com.unison.monitoring.api.domain.AlarmMapRegistry;
import com.unison.monitoring.api.entity.*;
import com.unison.monitoring.api.repository.*;
import com.unison.monitoring.security.UserDetailImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

    @Service
@RequiredArgsConstructor
public class DataManagementServiceImpl implements DataManagementService{

    private final AlarmRepository alarmRepository;
    private final DataRepository dataRepository;
    private final GeneralOverviewRepository generalOverviewRepository;
    private final PowerCurveRepository powerCurveRepository;
    private final RemarkDataRepository remarkDataRepository;
    private final RemarkMetaRepository remarkMetaRepository;
    private final ArchivedDataRepository archivedDataRepository;
    private final AlarmMapRegistry alarmMapRegistry;

    @Override
    public LocalDateTime getLastUploadDate() {
        return null;
    }

    @Override
    public void uploadData(List<ReportData> reportDataList, UUID turbineUuid) throws Exception {

        List<DataEntity> dataEntityList = new ArrayList<>();

        //find uuid of report data
        Optional<ReportData> optionalReportData = reportDataList.stream().findFirst();
        ReportData firstReportData = optionalReportData.orElseThrow(Exception::new);
        Optional<GeneralOverviewEntity> optionalGeneralOverview = generalOverviewRepository.findById(turbineUuid);

        if(optionalGeneralOverview.isPresent()) {
            for (ReportData reportData : reportDataList) {

                dataEntityList.add(
                        DataEntity.builder()
                                .id(DataEntity.Id.builder()
                                        .timestamp(DateTimeUtils.parseISOLocalDateTime(reportData.getMeasureDate()))
                                        .generalOverviewEntity(optionalGeneralOverview.get())
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
    }

    @Override
    public void uploadAlarms(List<AlarmDto.Response> alarmList, UUID turbineUuid) throws Exception {
        List<AlarmEntity> alarmEntityList = new ArrayList<>();

        if(alarmList == null || alarmList.isEmpty()){
            return;
        }

        if(generalOverviewRepository.findById(turbineUuid).isPresent()) {
            for (AlarmDto.Response alarm : alarmList) {

                alarmEntityList.add(
                        AlarmEntity.builder()
                                .id(
                                        AlarmEntity.Id.builder()
                                                .timestamp(DateTimeUtils.parseLocalDateTime(alarm.getAlarmLogTimestamp()))
                                                .alarmNumber(alarm.getAlarmNumber())
                                                .turbineUuid(turbineUuid)
                                                .build()
                                )
                                .alarmCode(alarm.getAlarmCode())
                                .comment(alarm.getComment())
                                .build()
                );
            }
            alarmRepository.saveAll(alarmEntityList);
        }
    }

    @Override
    public ReportDto.Response createAndWriteHeaderTable(UUID turbineUuid, LocalDateTime writeDate) throws Exception {
        if(writeDate.getHour() != 0 || writeDate.getMinute() != 0 || writeDate.getSecond() != 0){
            throw new Exception("writeDate is wrong " + writeDate);
        }

        ArchivedDataEntity archivedDataEntity = archivedDataRepository.findById(turbineUuid)
                .orElseThrow(() -> new Exception("not found archived data because it is wrong uuid: " + turbineUuid));

        String operatingPeriod = DateTimeUtils.formatLocalDateTime("`yy.MM.dd 00:00 ~ 24:00", writeDate);

        GeneralOverviewEntity generalOverviewEntity = generalOverviewRepository.findById(turbineUuid)
                .orElseThrow(() -> new Exception("not found turbine uuid: " + turbineUuid));

        LocalDateTime commissionStartDate = generalOverviewEntity.getCommissionDate();
        DailyDataStats totalDataStats = dataRepository.findDailyDataByUuidAnd(turbineUuid, commissionStartDate, writeDate.plusDays(1)).orElseThrow();
        Double totalActivePower = dataRepository.findTotalActivePowerLikeScada(turbineUuid, commissionStartDate, writeDate.plusDays(1)).orElseThrow();

        ReportDto.Response result;

        try{
            DailyDataStats DailyDataStats = dataRepository.findDailyDataByUuidAnd(turbineUuid, writeDate, writeDate.plusDays(1)).orElseThrow();

            result = ReportDto.Response.builder()
                    .date(writeDate)
                    .operatingPeriod(operatingPeriod)
                    .writtenDate(LocalDateTime.now())
                    .windSpeed(DailyDataStats.getAverageWindSpeed())
                    .activePower(DailyDataStats.getTotalActivePower() / 6)
                    .operatingTime(DailyDataStats.getOperatingTime().intValue())
                    .generatingTime(DailyDataStats.getGeneratingTime().intValue())
                    .startDate(commissionStartDate)
                    .totalActivePower((totalActivePower * 24) + archivedDataEntity.getActivePower())
                    .totalOperatingTime(totalDataStats.getOperatingTime().intValue() + archivedDataEntity.getOperatingTime())
                    .totalGeneratingTime(totalDataStats.getGeneratingTime().intValue() + archivedDataEntity.getGeneratingTime())
                    .ratedPower(generalOverviewEntity.getRatedPower())
                    .build();
        }catch(Exception e){
            result = ReportDto.Response.builder()
                    .date(writeDate)
                    .operatingPeriod(operatingPeriod)
                    .writtenDate(LocalDateTime.now())
                    .windSpeed(0.0)
                    .activePower(0.0)
                    .operatingTime(0)
                    .generatingTime(0)
                    .startDate(commissionStartDate)
                    .totalActivePower((totalActivePower * 24) + archivedDataEntity.getActivePower())
                    .totalOperatingTime(totalDataStats.getOperatingTime().intValue() + archivedDataEntity.getOperatingTime())
                    .totalGeneratingTime(totalDataStats.getGeneratingTime().intValue() + archivedDataEntity.getGeneratingTime())
                    .ratedPower(generalOverviewEntity.getRatedPower())
                    .build();
        }
        return result;
    }

    @Override
    public List<ReportDto.PowerCurve> getReferencePowerCurve(UUID turbineUuid) {

        List<PowerCurveEntity> powerCurveEntities = powerCurveRepository.findByGeneralOverviewEntityUuid(turbineUuid);

        return DataMapper.convertToReportDtoReferencePowerCurve(powerCurveEntities);
    }

    @Override
    public List<ReportDto.PowerCurve> getPowerCurveByTime(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate) {

        LocalDateTime firstQuarter = LocalDateTime.of(startDate.getYear(), startDate.getMonth().firstMonthOfQuarter() , 1, 0, 0);

        List<DataEntity> dataEntities = dataRepository.findByUuidAndTimestamp(turbineUuid, firstQuarter, endDate);

        return DataMapper.convertToReportDtoPowerCurveScatters(dataEntities);
    }

    @Override
    public List<ReportDto.TimeChart> getTimeChartDataList(UUID turbineUuid, LocalDateTime date) {

        LocalDateTime startDate = date;
        LocalDateTime endDate = date.plusDays(1);

        List<DataEntity> dataEntities = dataRepository.findByUuidAndTimestamp(turbineUuid, startDate, endDate);


        if(dataEntities.size() < 144){
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
        return DataMapper.convertToReportDtoTimeChartGroups(dataEntities);
    }

    @Override
    public List<ReportDto.Alarm> getAlarmsByTime(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate) {
        List<AlarmEntity> alarmEntities = alarmRepository.findByTurbineUuidAndTimestamp(turbineUuid, startDate, endDate);

        Map<String, String> alarmsMap = alarmMapRegistry.getAlarmMap(turbineUuid);

        return DataMapper.convertToReportDtoAlarmsGroups(alarmEntities, alarmsMap);
    }

    @Override
    public List<ReportDto.Remark> getRemarksByTime(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate) {
        List<RemarkMetaEntity> remarkMetaEntities = remarkMetaRepository.findByGeneralOverviewEntityUuidOrderByOrderId(turbineUuid);

        List<Integer> remarkIds = remarkMetaEntities.stream()
                .map(RemarkMetaEntity::getOrderId)
                .toList();

        List<RemarkDataEntity> remarkEntities = remarkDataRepository.findByUuidAndTimestampOrderById(turbineUuid, startDate, endDate);

        if(remarkEntities.isEmpty()){
            remarkEntities = remarkMetaEntities.stream()
                    .map(remarkMetaEntity ->
                            RemarkDataEntity.builder()
                                    .uuid(Constants.DEFAULT_UUID)
                                    .remarkMetaEntity(remarkMetaEntity)
                                    .timestamp(startDate)
                                    .description(remarkMetaEntity.getDefaultDescription())
                                    .build()
                    )
                    .toList();
        }
        return DataMapper.convertToReportDtoRemarks(remarkEntities);
    }

    @Override
    public List<SitesDto.Response> getSites() {
        List<GeneralOverviewEntity> generalOverviewEntities = generalOverviewRepository.findByIsActiveTrue();

        return DataMapper.convertToSitesDtoResponse(generalOverviewEntities);
    }

    @Override
    public List<Resource<RemarkDto.Response>> createRemarks(ApiRequests<RemarkDto.Request> request) throws Exception {
        List<Resource<RemarkDto.Response>> response;
        System.out.println("createRemarks");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();


        UUID turbineUuid = request.getData().stream()
                .findFirst()
                .orElseThrow()
                .getAttributes()
                .getTurbineUuid();

        List<RemarkMetaEntity> remarkMetaEntities = remarkMetaRepository.findByGeneralOverviewEntityUuidOrderByOrderId(turbineUuid);

        if(remarkMetaEntities.isEmpty())
            throw new Exception("Meta Entity empty");

        List<RemarkDataEntity> remarkDataEntities = request.getData().stream()
                .map(dataInRequest ->
                         RemarkDataEntity.builder()
                                .uuid(UUID.fromString(dataInRequest.getId()))
                                .timestamp(dataInRequest.getAttributes().getTimestamp())
                                .description(dataInRequest.getAttributes().getContent())
                                .remarkMetaEntity(
                                        remarkMetaEntities.stream()
                                            .filter(remarkMetaEntity -> remarkMetaEntity.getTitle().equals(dataInRequest.getAttributes().getTitle()))
                                            .findFirst()
                                            .orElseThrow()
                                )
                                .createdBy(userDetail.getMember().getId())
                                .createdAt(LocalDateTime.now())
                                .generalOverviewEntity(new GeneralOverviewEntity(dataInRequest.getAttributes().getTurbineUuid()))
                                .build()
                ).toList();

        remarkDataRepository.saveAll(remarkDataEntities);

        response = DataMapper.convertToRemarkDtoResponse(request);

        return response;
    }

        @Override
        @Transactional
        public List<Resource<RemarkDto.Response>> updateRemarks(ApiRequests<RemarkDto.Request> request) throws Exception {
            List<Resource<RemarkDto.Response>> response;

            List<RemarkDataEntity> changeRemarkDataEntities = request.getData().stream()
                                    .map(dataInRequest -> RemarkDataEntity.builder()
                                            .uuid(UUID.fromString(dataInRequest.getId()))
                                            .description(dataInRequest.getAttributes().getContent())
                                            .build())
                                    .toList();

            List<RemarkDataEntity> originRemarkDataEntities = remarkDataRepository.findByUuidIn(
                    changeRemarkDataEntities.stream()
                            .map(RemarkDataEntity::getId)
                            .toList()
            );

            List<RemarkDataEntity> updateRemarkDataEntities = originRemarkDataEntities.stream()
                    .map(remarkDataEntity ->
                            {
                                remarkDataEntity.setDescription(
                                        changeRemarkDataEntities.stream()
                                            .filter(changeRemarkDataEntity ->
                                                    changeRemarkDataEntity.getUuid().equals(remarkDataEntity.getId())
                                            )
                                            .findFirst()
                                            .orElseThrow()
                                            .getDescription()
                                );
                                return remarkDataEntity;
                            }
                    )
                    .toList();

            remarkDataRepository.saveAll(updateRemarkDataEntities);

            response = DataMapper.convertToRemarkDtoResponse(request);

            return response;
        }

        @Override
        @Transactional
        public Resource<DailyReportRemarkDto.Response> updateDailyReportRemark(ApiRequest<DailyReportRemarkDto.Request> request) throws Exception {
            Resource<DailyReportRemarkDto.Response> response;

            UUID turbineUuid = UUID.fromString(request.getData().getId());

            Optional<GeneralOverviewEntity> optionalGeneralOverviewEntity = generalOverviewRepository.findById(turbineUuid);

            GeneralOverviewEntity generalOverviewEntity = optionalGeneralOverviewEntity.orElseThrow(() -> new Exception("Not found turbine uuid"));

            String newRemark = request.getData().getAttributes().getRemark();


            if (!newRemark.equals(generalOverviewEntity.getRemark())) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();

                generalOverviewEntity.updateRemark(newRemark, userDetail);
                generalOverviewRepository.save(generalOverviewEntity);
            }

            response = DataMapper.convertToDailyReportRemarkDtoResponse(request);

            return response;
        }
    }
