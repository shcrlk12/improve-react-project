package com.unison.monitoring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "batch-server")
@Getter
@Setter
public class BatchServerProperties {
    private String u113;
    private String u113Port;

    private String u120;
    private String u120Port;

    private String u151;
    private String u151Port;

    public String getU113Domain() {
        return u113 + ":" + u113Port ;
    }

    public String getU120Domain() {
        return u120 + ":" + u120Port ;
    }

    public String getU151Domain() {
        return u151 + ":" + u151Port ;
    }
}
