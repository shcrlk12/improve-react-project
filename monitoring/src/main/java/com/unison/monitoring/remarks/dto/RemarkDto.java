package com.unison.monitoring.remarks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemarkDto {
    @JsonProperty("uuid")
    private UUID turbineUuid;
    private LocalDateTime timestamp;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    private int order;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
