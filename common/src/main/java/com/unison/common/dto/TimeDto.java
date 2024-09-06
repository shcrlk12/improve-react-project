package com.unison.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class TimeDto {
    public final static String TYPE = "times";

    @Setter
    @Getter
    @RequiredArgsConstructor
    public static class Response{
        private final String time;
    }
}
