package com.unison.monitoring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final ApiServerProperties apiServerProperties;

    @Bean
    public WebClient webClient(){
        return WebClient.create(
                String.format("http://%s:%s", apiServerProperties.getIp(), apiServerProperties.getPort())
        );
    }
}
