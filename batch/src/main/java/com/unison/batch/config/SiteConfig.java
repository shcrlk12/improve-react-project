package com.unison.batch.config;

import com.unison.batch.service.U113UploadBatchService;
import com.unison.batch.service.U120UploadBatchService;
import com.unison.batch.service.U151UploadBatchService;
import com.unison.batch.service.UploadBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class SiteConfig {

    private final SiteProperties siteProperties;

    private final JdbcTemplate jdbcTemplate;
    @Bean
    public UploadBatchService uploadBatchService() {
        if("u113".equals(siteProperties.getType())) {
            return new U113UploadBatchService(jdbcTemplate);
        }
        else if("u120".equals(siteProperties.getType())) {
            return new U120UploadBatchService(jdbcTemplate);
        }
        else if("u151".equals(siteProperties.getType())) {
            return new U151UploadBatchService(jdbcTemplate);
        }
        else {
            return null;
        }
    }
}
