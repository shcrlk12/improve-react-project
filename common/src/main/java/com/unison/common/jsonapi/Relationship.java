package com.unison.common.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
public class Relationship {
    private Links links;
    private ResourceLinkage data;  // 링크된 리소스

    // 기본 생성자, getters, setters, equals, hashCode, toString
}