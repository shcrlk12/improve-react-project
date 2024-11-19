package com.unison.monitoring.api;

import com.unison.common.jsonapi.response.ApiResponse;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApiResponseErrorsBuilder {

    private static List<ApiResponse.Error> createErrorResponse(String status, String title, String detail) {
        return Stream.of(
                ApiResponse.Error.builder()
                        .status(status)
                        .title(title)
                        .detail(detail)
                        .build()
        ).collect(Collectors.toList());
    }


    public static List<ApiResponse.Error> buildInternalServerErrorResponse() {
        return createErrorResponse("500", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }
    public static List<ApiResponse.Error> buildInternalServerErrorResponse(String detail) {
        return createErrorResponse("500", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), detail);
    }
}