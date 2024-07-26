package com.unison.monitoring.api.jsonapiorg;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
public class Resource<T> {
    private String type;
    private String id;
    private T attributes;
    private Map<String, Relationship> relationships;
    private Map<String, Object> meta;  // Optional

    // 기본 생성자, getters, setters, equals, hashCode, toString
}