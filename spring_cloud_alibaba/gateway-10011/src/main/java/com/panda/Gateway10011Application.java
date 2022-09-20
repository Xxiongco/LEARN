package com.panda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Gateway10011Application {

    public static void main(String[] args) {
        SpringApplication.run(Gateway10011Application.class, args);
    }

}
