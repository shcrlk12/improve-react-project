package com.unison.monitoring.api.data;

import com.unison.common.dto.ReportDataDto;
import com.unison.common.dto.TimeDto;
import com.unison.common.jsonapi.JsonApi;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.Links;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.common.jsonapi.response.ApiResponse;
import com.unison.monitoring.api.data.dto.ReportDto;
import com.unison.monitoring.api.data.service.DataManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {
    private final DataManagementService dataManagementService;

    @GetMapping("/last-update")
    public ResponseEntity<com.unison.common.jsonapi.response.ApiResponse<TimeDto.Response>> getLastUploadDate(HttpServletRequest httpServletRequest){
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        Resource<TimeDto.Response> resource = Resource.<TimeDto.Response>builder()
                .type(TimeDto.TYPE)
                .id("31f8eca8-228f-424a-9d14-98563c852bcf") //TODO need to change
                .attributes(new TimeDto.Response(LocalDateTime.of(2024,5,1,0,0, 0).toString()))
                .build();

        com.unison.common.jsonapi.response.ApiResponse<TimeDto.Response> apiResponse = com.unison.common.jsonapi.response.ApiResponse.<TimeDto.Response>builder()
                .data(resource)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponse);
    }

    @PostMapping
    public void uploadData(@RequestBody ApiRequests<ReportDataDto.Response> request) throws Exception {
//        dataManagementService.uploadData(DataMapper.apiResponseToListReportData(request));
    }

    @GetMapping("/report/u151")
    public ResponseEntity<ApiResponse<ReportDto.Response>> u151Report(HttpServletRequest httpServletRequest){
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        //1. 위에 표 작성할 내용
        ReportDto.Response attributes = ReportDto.Response.builder()
                .date(LocalDateTime.of(2024, 9, 3, 0, 0))
                .operatingPeriod("`24.08.27 00:00 ~ 24:00")
                .writtenDate(LocalDateTime.of(2024, 8, 28, 0, 0))
                .windSpeed(3.47115641)
                .activePower(9203.391651651)
                .operatingTime(62185) // 17h 16m 25s
                .generatingTime(27445) // 7h 37m 25s
                .startDate(LocalDateTime.of(2019, 9, 3, 0, 0))
                .totalActivePower(38509959.0215616)
                .totalOperatingTime(137236980)
                .totalGeneratingTime(88034400)
                .build();


        //2 reference power curve
        List<ReportDto.PowerCurve> referencePowerCurve = new ArrayList<>();

        referencePowerCurve.add(new ReportDto.PowerCurve(3.0, 60.2118));
        referencePowerCurve.add(new ReportDto.PowerCurve(3.5, 141.971));
        referencePowerCurve.add(new ReportDto.PowerCurve(4.0, 245.051));
        referencePowerCurve.add(new ReportDto.PowerCurve(4.5, 366.097));
        referencePowerCurve.add(new ReportDto.PowerCurve(5.0, 522.061));
        referencePowerCurve.add(new ReportDto.PowerCurve(5.5, 718.677));
        referencePowerCurve.add(new ReportDto.PowerCurve(6.0, 958.851));
        referencePowerCurve.add(new ReportDto.PowerCurve(6.5, 1246.71));
        referencePowerCurve.add(new ReportDto.PowerCurve(7.0, 1590.22));
        referencePowerCurve.add(new ReportDto.PowerCurve(7.5, 1981.7));
        referencePowerCurve.add(new ReportDto.PowerCurve(8.0, 2432.72));
        referencePowerCurve.add(new ReportDto.PowerCurve(8.5, 2887.12));
        referencePowerCurve.add(new ReportDto.PowerCurve(9.0, 3355.21));
        referencePowerCurve.add(new ReportDto.PowerCurve(9.5, 3828.27));
        referencePowerCurve.add(new ReportDto.PowerCurve(10.0, 4300.0));
        referencePowerCurve.add(new ReportDto.PowerCurve(20.0, 4300.0));

        attributes.setReferencePowerCurve(referencePowerCurve);
        //2 power curve 데이터
        List<ReportDto.PowerCurve> powerCurveScatter = new ArrayList<>();

        powerCurveScatter.add(new ReportDto.PowerCurve(4.846, 521.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(6.070, 1034.504));
        powerCurveScatter.add(new ReportDto.PowerCurve(5.534, 855.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(4.889, 534.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(4.775, 474.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(4.853, 490.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(6.507, 1356.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(7.009, 1636.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(6.428, 1320.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(5.768, 844.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(5.565, 751.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(3.851, 180.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(5.846, 521.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(7.070, 1034.504));
        powerCurveScatter.add(new ReportDto.PowerCurve(6.534, 855.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(5.889, 534.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(5.775, 474.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(5.853, 490.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(7.507, 1356.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(8.009, 1636.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(7.428, 1320.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(6.768, 844.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(6.565, 751.407));
        powerCurveScatter.add(new ReportDto.PowerCurve(4.851, 180.407));

        attributes.setPowerCurveScatter(powerCurveScatter);

        //3 time chart 데이터
        List<ReportDto.TimeChart> timeChartData = new ArrayList<>();

        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 0, 0),6.183,4.31,327.678));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 0, 10),7.665,5.262,774.395));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 0, 20),6.73,4.753,471.878));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 0, 30),7.192,5.138,601.708));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 0, 40),6.003,3.558,168.27));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 0, 50),6.017,3.853,212.433));

        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 1, 0),6.183,4.31,327.678));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 1, 10),7.665,5.262,774.395));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 1, 20),6.73,4.753,471.878));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 1, 30),7.192,5.138,601.708));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 1, 40),6.003,3.558,168.27));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 1, 50),6.017,3.853,212.433));

        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 2, 0),6.183,4.31,327.678));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 2, 10),7.665,5.262,774.395));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 2, 20),6.73,4.753,471.878));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 2, 30),7.192,5.138,601.708));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 2, 40),6.003,3.558,168.27));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 2, 50),6.017,3.853,212.433));

        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 3, 0),6.183,4.31,327.678));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 3, 10),7.665,5.262,774.395));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 3, 20),6.73,4.753,471.878));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 3, 30),7.192,5.138,601.708));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 3, 40),6.003,3.558,168.27));
        timeChartData.add(new ReportDto.TimeChart(LocalDateTime.of(2024, 9, 3, 3, 50),6.017,3.853,212.433));

        attributes.setTimeChart(timeChartData);
        //4 alarm 데이터
        List<ReportDto.Alarms> alarmList = new ArrayList<>();

        alarmList.add(new ReportDto.Alarms(LocalDateTime.now(), 20011, "Low wind speed", ""));
        alarmList.add(new ReportDto.Alarms(LocalDateTime.now(), 20027, "Cable twist 2 turns CW", ""));
        alarmList.add(new ReportDto.Alarms(LocalDateTime.now(), 20011, "Low wind speed", ""));
        alarmList.add(new ReportDto.Alarms(LocalDateTime.now(), 20026, "HYD. Rotor brake storage pressure low", ""));

        attributes.setAlarms(alarmList);


        //5 remarks 데이터
        List<ReportDto.Remark> remarkList = new ArrayList<>();

        remarkList.add(new ReportDto.Remark("주요 이벤트 현황", "1. [20011] HYD. Rotor brake storage pressure low 알람 PCS 점검 (백규열 주임) - 21:56 ~ 22:01"));
        remarkList.add(new ReportDto.Remark("주요 에러 발생현황 및 조치사항", "1. 특이사항 있음."));
        remarkList.add(new ReportDto.Remark("현장이슈 발생현황", "특이사항 없음."));

        attributes.setEventBoxNotes(remarkList);

        Resource<ReportDto.Response> resource = Resource.<ReportDto.Response>builder()
                .type(ReportDto.TYPE)
                .id(UUID.randomUUID().toString()) //TODO need to change
                .attributes(attributes)
                .build();

        ApiResponse<ReportDto.Response> apiRequests = ApiResponse.<ReportDto.Response>builder()
                .data(resource)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiRequests);
    }
}
