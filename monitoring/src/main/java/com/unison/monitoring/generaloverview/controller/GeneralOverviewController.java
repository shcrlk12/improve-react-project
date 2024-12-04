package com.unison.monitoring.generaloverview.controller;

import com.unison.common.dto.DailyReportRemarkDto;
import com.unison.common.dto.SitesDto;
import com.unison.common.jsonapi.JsonApi;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.Links;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.common.jsonapi.response.ApiResponses;
import com.unison.monitoring.generaloverview.service.GeneralOverviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GeneralOverviewController {
    private final GeneralOverviewService generalOverviewService;

    @GetMapping("/overview")
    public ResponseEntity<ApiResponses<SitesDto.Response>> getSites(HttpServletRequest httpServletRequest){
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        List<Resource<SitesDto.Response>> resources = generalOverviewService.findAll()
                .stream()
                .map(data -> Resource.<SitesDto.Response>builder()
                        .id(data.getTurbineUuid().toString())
                        .type(SitesDto.TYPE)
                        .attributes(new SitesDto.Response(data.getTurbineUuid(), data.getSiteName(), data.getRemark(), data.getRatedPower()))
                        .build())
                .collect(Collectors.toList());

        com.unison.common.jsonapi.response.ApiResponses<SitesDto.Response> apiResponses = com.unison.common.jsonapi.response.ApiResponses.<SitesDto.Response>builder()
                .data(resources)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponses);
    }


}
