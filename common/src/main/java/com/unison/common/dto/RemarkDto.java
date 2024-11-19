package com.unison.common.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class RemarkDto {
    public static String TYPE = "remarks";

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String title;
        private String content;
        private UUID turbineUuid;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime timestamp;
    }

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String title;
        private String content;
        private UUID turbineUuid;
    }
}
