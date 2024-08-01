package com.unison.batch.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
public class Links {
    private String self;
    private String related;  // Optional
    private String first;  // Optional
    private String last;  // Optional
    private String prev;  // Optional
    private String next;  // Optional

    public Links(String self) {
        this.self = self;
    }

    // 기본 생성자, getters, setters, equals, hashCode, toString
}