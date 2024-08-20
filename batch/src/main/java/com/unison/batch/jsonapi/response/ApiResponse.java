package com.unison.batch.jsonapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unison.batch.jsonapi.JsonApi;
import com.unison.batch.jsonapi.Links;
import com.unison.batch.jsonapi.Resource;
import lombok.*;

import java.util.List;
import java.util.Map;


/*
 * @see https://jsonapi.org/
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
public class ApiResponse<T> {
    private Resource<T> data; // Optional at least one
    private List<Error> errors;  // Optional at least one
    private Map<String, Object> meta;  // Optional at least one
    private Links links;  // Optional
    private JsonApi jsonapi; // Optional
    private List<Resource<T>> included; // Optional

    public ApiResponse(Resource<T> data) {
        this.data = data;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
    public static class Error {
        private String id;  // Optional
        private String status;
        private String code;  // Optional
        private String title;
        private String detail;  // Optional
        private Map<String, Object> meta;  // Optional

        // 기본 생성자, getters, setters, equals, hashCode, toString
    }
}