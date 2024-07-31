package com.unison.batch;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@Configuration
public class R2dbcEntityTemplateConfig {
    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mssql")
                .option(ConnectionFactoryOptions.PROTOCOL, "tcp")
                .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
                .option(ConnectionFactoryOptions.PORT, 1433)
                .option(ConnectionFactoryOptions.DATABASE, "WPPIS_DB_U120")
                .option(ConnectionFactoryOptions.USER, "your-username")
                .option(ConnectionFactoryOptions.PASSWORD, "your-password")
                .build();


        ConnectionFactory factory = ConnectionFactories.get(options);



        return new R2dbcEntityTemplate(factory);
    }
}
