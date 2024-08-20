package com.unison.batch.service;


import com.unison.batch.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.batch.jsonapi.Resource;
import com.unison.batch.jsonapi.request.ApiRequests;
import com.unison.batch.jsonapi.response.ApiResponse;
import com.unison.batch.domain.ReportData;
import com.unison.batch.dto.TimeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class U113UploadBatchServiceTests {

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @Autowired
    private U113UploadBatchService u113UploadBatchService;

    @DisplayName("10 분 데이터 조회 테스트")
    @Test
    public void testGetReportData() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2024, 5, 2, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 5, 3, 0, 0, 0);

        // When

        Mono<List<ReportData>> actualData = u113UploadBatchService.getReportData(startDate, endDate);

        // Then
        StepVerifier.create(actualData)
                .expectNextMatches(data ->
                    data.size() == 144
                )
                .expectComplete()
                .verify();
    }

    @DisplayName("마지막 날짜 조회 테스트")
    @Test
    public void testRetrieveLastUploadDate()
    {
        //Given
        LocalDateTime expectedDate = LocalDateTime.of(2024, 7, 3, 0, 0, 0);

        Resource<TimeDto.Response> resource = new Resource<>("1", TimeDto.TYPE, new TimeDto.Response("2024-07-03 00:00:00"));

        ApiResponse<TimeDto.Response> apiResponse = new ApiResponse<>(resource);

        ResponseEntity<ApiResponse<TimeDto.Response>> responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .headers(new JsonApiOrgHttpHeaders())
                .body(apiResponse);

        when(webClient.get()
                .uri(URI.create("/api/data/last-update"))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ApiResponse<TimeDto.Response>>() {})
        ).thenReturn(Mono.just(responseEntity));


        //When
        Mono<LocalDateTime> actualData = u113UploadBatchService.retrieveLastUploadDate();

        // Then
        StepVerifier.create(actualData)
            .expectNextMatches(date ->
                    date.getYear() == expectedDate.getYear() &&
                    date.getMonth() == expectedDate.getMonth() &&
                    date.getDayOfMonth() == expectedDate.getDayOfMonth() &&
                    date.getHour() == 0 &&
                    date.getMinute() == 0 &&
                    date.getSecond() == 0 &&
                    date.getNano() == 0
            )
            .expectComplete()
            .verify();
    }

    @Test
    public void testUploadData() {
        // Given
        ApiRequests<?> request = new ApiRequests<>();

        WebClient.RequestBodyUriSpec requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = Mockito.mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(URI.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(ApiRequests.class), eq(ApiRequests.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // When
        u113UploadBatchService.uploadData(request);

        InOrder inOrder = inOrder(webClient.post(), requestBodySpec, requestHeadersSpec);

        // Then
        inOrder.verify(webClient.post()).uri(URI.create("/api/data"));
        inOrder.verify(requestBodySpec).body(request, ApiRequests.class);
        inOrder.verify(requestHeadersSpec).retrieve();
    }
}