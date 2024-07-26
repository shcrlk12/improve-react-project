package com.unison.monitoring.api.member.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;


public class MemberDto {
    @Builder
    @Getter
    public static class Request{
        String id;
        String pw;
        String role;
        String name;
    }
    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{
        String id;
        String role;
        String name;
    }
}
