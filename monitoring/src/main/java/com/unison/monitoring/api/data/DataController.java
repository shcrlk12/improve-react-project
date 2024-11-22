package com.unison.monitoring.api.data;

import com.unison.common.Constants;
import com.unison.common.dto.*;
import com.unison.common.jsonapi.JsonApi;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.Links;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.common.jsonapi.response.ApiResponse;
import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.api.ApiResponseErrorsBuilder;
import com.unison.monitoring.api.data.dto.ReportDto;
import com.unison.monitoring.api.data.service.DataManagementService;
import com.unison.monitoring.api.entity.MemberEntity;
import com.unison.monitoring.security.UserDetailImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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

    @GetMapping("auth")
    public ResponseEntity<ApiResponse<UserDto.Response>> auth(HttpServletRequest httpServletRequest){
        MemberEntity memberEntity = ((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember();

        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        Resource<UserDto.Response> resource = Resource.<UserDto.Response>builder()
                .id(memberEntity.getId())
                .type(UserDto.TYPE)
                .attributes(new UserDto.Response(memberEntity.getRole(), memberEntity.getName(), "인증 성공"))
                .build();
        
        ApiResponse<UserDto.Response> apiResponse = ApiResponse.<UserDto.Response>builder()
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
    @Getter
    @AllArgsConstructor
    public static class TimeObject {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime writeDate;
    }


    @GetMapping("/report")
    public ResponseEntity<ApiResponse<ReportDto.Response>> u151Report(HttpServletRequest httpServletRequest, UUID turbineUuid, TimeObject timeObject){
        LocalDateTime writeDate = timeObject.getWriteDate();
        ZonedDateTime koreanWriteDate = writeDate.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of("Asia/Seoul"));


        LocalDateTime startDate = LocalDateTime.of(koreanWriteDate.getYear(), koreanWriteDate.getMonth(), koreanWriteDate.getDayOfMonth(), 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);

        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        try{
            //1. 위에 표 작성할 내용
            ReportDto.Response attributes = dataManagementService.createAndWriteHeaderTable(turbineUuid, startDate);

            //2 reference power curve
            attributes.setReferencePowerCurve(dataManagementService.getReferencePowerCurve(turbineUuid));

            //2 power curve 데이터
            attributes.setPowerCurveScatter(dataManagementService.getPowerCurveByTime(turbineUuid, startDate, endDate));

            //3 time chart 데이터
            attributes.setTimeChart(dataManagementService.getTimeChartDataList(turbineUuid, startDate));

            //4 alarm 데이터
            attributes.setAlarms(dataManagementService.getAlarmsByTime(turbineUuid, startDate, endDate));


            //5 remarks 데이터
            attributes.setEventBoxNotes(dataManagementService.getRemarksByTime(turbineUuid, startDate, endDate));

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

        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .body(ApiResponse.<ReportDto.Response>builder()
                        .errors(ApiResponseErrorsBuilder.buildInternalServerErrorResponse())
                        .build()
                );
    }

    @GetMapping("/sites")
    public ResponseEntity<com.unison.common.jsonapi.response.ApiResponses<SitesDto.Response>> getSites(HttpServletRequest httpServletRequest){
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        List<Resource<SitesDto.Response>> resources = new ArrayList<>();

        dataManagementService.getSites()
                .forEach(site ->
                    resources.add(Resource.<SitesDto.Response>builder()
                                    .id(site.getUuid().toString())
                                    .type(SitesDto.TYPE)
                                    .attributes(new SitesDto.Response(null, site.getName(), site.getRemark(), site.getRatedPower()))
                                    .build())
                );

        com.unison.common.jsonapi.response.ApiResponses<SitesDto.Response> apiResponses = com.unison.common.jsonapi.response.ApiResponses.<SitesDto.Response>builder()
                .data(resources)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponses);
    }

    @PostMapping("/remarks")
    public ResponseEntity<com.unison.common.jsonapi.response.ApiResponses<RemarkDto.Response>> createRemarks(HttpServletRequest httpServletRequest, @RequestBody ApiRequests<RemarkDto.Request> request) throws Exception {
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        List<Resource<RemarkDto.Response>> resources = dataManagementService.createRemarks(request);

        com.unison.common.jsonapi.response.ApiResponses<RemarkDto.Response> apiResponses = com.unison.common.jsonapi.response.ApiResponses.<RemarkDto.Response>builder()
                .data(resources)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(apiResponses);
    }

    @PatchMapping("/remarks")
    public ResponseEntity<com.unison.common.jsonapi.response.ApiResponses<RemarkDto.Response>> updateRemarks(HttpServletRequest httpServletRequest, @RequestBody ApiRequests<RemarkDto.Request> request) throws Exception {
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        List<Resource<RemarkDto.Response>> resources = dataManagementService.updateRemarks(request);

        com.unison.common.jsonapi.response.ApiResponses<RemarkDto.Response> apiResponses = com.unison.common.jsonapi.response.ApiResponses.<RemarkDto.Response>builder()
                .data(resources)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(apiResponses);
    }

    @PatchMapping("/remarks/{turbineUuid}")
    public ResponseEntity<com.unison.common.jsonapi.response.ApiResponse<DailyReportRemarkDto.Response>> updateRemarks(HttpServletRequest httpServletRequest, @RequestBody ApiRequest<DailyReportRemarkDto.Request> request, @PathVariable("turbineUuid") UUID turbineUuid) throws Exception {
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        Resource<DailyReportRemarkDto.Response> resource = dataManagementService.updateDailyReportRemark(request);

        com.unison.common.jsonapi.response.ApiResponse<DailyReportRemarkDto.Response> apiResponses = com.unison.common.jsonapi.response.ApiResponse.<DailyReportRemarkDto.Response>builder()
                .data(resource)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(apiResponses);
    }
}
