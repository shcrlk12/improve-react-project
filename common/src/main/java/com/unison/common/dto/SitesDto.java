package com.unison.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Locale;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class SitesDto {
    public final static String TYPE = "sites";

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{
        private UUID uuid;
        private String name;
        private String remark;
        private Integer ratedPower;

        public String getName() {
            return name.toUpperCase(Locale.ROOT);
        }
    }

}
