package com.panda.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * ElasticSearch 客户端配置
 *
 * @author geng
 * 2020/12/19
 */
@Configuration
public class RestClientConfig {

    @Autowired
    private EsProperty esProperty;

    @Bean
    public RestHighLevelClient restHighLevelClient(){

        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(Optional.ofNullable(esProperty.getHostName()).orElse("localhost"),
                        Optional.ofNullable(esProperty.getPort()).orElse(9200),
                        Optional.ofNullable(esProperty.getScheme()).orElse("http"))));
    }
}
