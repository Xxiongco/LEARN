package com.panda.provider8083;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Provider8082Application {

    public static void main(String[] args) {
        SpringApplication.run(Provider8082Application.class, args);
    }

}