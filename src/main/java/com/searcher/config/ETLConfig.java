package com.searcher.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by ss on 2017/6/9.
 */

@Data
@Component
@EnableConfigurationProperties
@PropertySource("classpath:settings.properties")
@ConfigurationProperties(prefix = "ETL")
public class ETLConfig {

    private String salePath;
    private String searchPath;
}