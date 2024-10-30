package com.unison.batch.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "site")
@Getter
@Setter
public class SiteProperties {
    private String type;
}
