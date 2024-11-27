package com.unison.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class MemberDto {
    public static String TYPE = "users";
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        String pw;
        String role;
        String name;
        String id;
    }
    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{

        @JsonIgnore
        String id;
        String role;
        String name;
    }
}
