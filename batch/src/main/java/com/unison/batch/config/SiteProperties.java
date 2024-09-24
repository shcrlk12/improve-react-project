package com.unison.batch.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "site")
@Getter
public class SiteProperties {
    private String type;
}
