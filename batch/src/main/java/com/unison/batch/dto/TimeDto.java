package com.unison.batch.dto;

import lombok.*;

public class TimeDto {
    public final static String TYPE = "times";

    @Setter
    @Getter
    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Response{
        private final String time;
    }
}