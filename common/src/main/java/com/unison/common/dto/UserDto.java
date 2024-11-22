package com.unison.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class UserDto {
    public final static String TYPE = "user";

    @Setter
    @Getter
    @RequiredArgsConstructor
    public static class Response{
        private final String role;
        private final String name;
        private final String message;

    }
}
