package com.unison.common.dto;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class DailyReportRemarkDto {
    public static String TYPE = "daily-remarks";

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String remark;
    }

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String remark;
    }
}
