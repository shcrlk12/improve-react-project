package com.unison.monitoring.remarks.controller;

import com.unison.common.dto.RemarkDto;
import com.unison.common.jsonapi.JsonApi;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.Links;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.common.jsonapi.response.ApiResponses;
import com.unison.monitoring.remarks.service.RemarkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RemarksController {

    private final RemarkService remarkService;

    @PostMapping("/{turbineUuid}/remarks")
    public ResponseEntity<ApiResponses<RemarkDto.Response>> createRemarks(HttpServletRequest httpServletRequest, @PathVariable("turbineUuid") UUID turbineUuid, @RequestBody ApiRequests<RemarkDto.Request> request) throws Exception {
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        List<Resource<RemarkDto.Response>> resources = remarkService.createRemarks(request);

        com.unison.common.jsonapi.response.ApiResponses<RemarkDto.Response> apiResponses = com.unison.common.jsonapi.response.ApiResponses.<RemarkDto.Response>builder()
                .data(resources)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(apiResponses);
    }

    @PatchMapping("/{turbineUuid}/remarks")
    public ResponseEntity<com.unison.common.jsonapi.response.ApiResponses<RemarkDto.Response>> updateRemarks(HttpServletRequest httpServletRequest, @PathVariable("turbineUuid") UUID turbineUuid, @RequestBody ApiRequests<RemarkDto.Request> request) throws Exception {
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        List<Resource<RemarkDto.Response>> resources = remarkService.updateRemarks(request);

        com.unison.common.jsonapi.response.ApiResponses<RemarkDto.Response> apiResponses = com.unison.common.jsonapi.response.ApiResponses.<RemarkDto.Response>builder()
                .data(resources)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(apiResponses);
    }
}
