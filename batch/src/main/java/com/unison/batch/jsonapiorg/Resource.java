package com.unison.batch.jsonapiorg;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
public class Resource<T> {
    private String id;
    private String type;
    private T attributes;
    private Map<String, Relationship> relationships;
    private Map<String, Object> meta;  // Optional

    public Resource(String id, String type, T attributes) {
        this.id = id;
        this.type = type;
        this.attributes = attributes;
    }
}