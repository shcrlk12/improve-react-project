package com.unison.batch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

public class LastUpdateDto {

    @Getter
    @Setter
    @Builder
    public static class Response{
        private final UUID uuid;
        private final LocalDateTime lastUpdateTime;
    }
}
