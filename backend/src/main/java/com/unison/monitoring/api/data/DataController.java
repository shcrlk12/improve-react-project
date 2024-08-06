package com.unison.monitoring.api.data;

import com.unison.monitoring.api.data.service.DataManagementService;
import com.unison.monitoring.api.dto.ReportDataDto;
import com.unison.monitoring.api.dto.TimeDto;
import com.unison.monitoring.api.jsonapi.JsonApi;
import com.unison.monitoring.api.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.monitoring.api.jsonapi.Links;
import com.unison.monitoring.api.jsonapi.Resource;
import com.unison.monitoring.api.jsonapi.request.ApiRequests;
import com.unison.monitoring.api.jsonapi.response.ApiResponse;
import com.unison.monitoring.api.mapper.DataMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {
    private final DataManagementService dataManagementService;

    @GetMapping("/last-update")
    public ResponseEntity<ApiResponse<TimeDto.Response>> getLastUploadDate(HttpServletRequest httpServletRequest){
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        Resource<TimeDto.Response> resource = Resource.<TimeDto.Response>builder()
                .type(TimeDto.TYPE)
                .id("31f8eca8-228f-424a-9d14-98563c852bcf") //TODO need to change
                .attributes(new TimeDto.Response(LocalDateTime.of(2024,5,1,0,0, 0).toString()))
                .build();

        ApiResponse<TimeDto.Response> apiResponse = ApiResponse.<TimeDto.Response>builder()
                .data(resource)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponse);
    }

    @PostMapping
    public void uploadData(@RequestBody ApiRequests<ReportDataDto.Request> request) throws Exception {
        dataManagementService.uploadData(DataMapper.apiRequestsToListRequest(request));
    }
}
