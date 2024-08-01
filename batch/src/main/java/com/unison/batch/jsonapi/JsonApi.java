package com.unison.batch.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Builder
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
public class JsonApi {
    private String version; //Optional
    private List<String> ext; //Optional
    private List<String> profile; //Optional
    private Map<String, Object> meta; //Optional

    public JsonApi() {
        version = "1.1";
    }
}
