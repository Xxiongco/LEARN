package com.panda.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@Data
@ConfigurationProperties(prefix = "es")
public class EsProperty {

    private String hostName;

    private Integer port;

    private String scheme;
}
