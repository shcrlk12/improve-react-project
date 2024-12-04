package com.unison.monitoring.common.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api-server")
@Getter
@Setter
public class ApiServerProperties {
    private String ip;
    private String port;
    private String domain;
}

