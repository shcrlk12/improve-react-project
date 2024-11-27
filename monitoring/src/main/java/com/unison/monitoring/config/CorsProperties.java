package com.unison.monitoring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cors")
@Getter
@Setter
public class CorsProperties {
    private String protocol;
    private String ip;
    private String port;
    private String domain;
}
