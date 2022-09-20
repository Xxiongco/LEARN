package com.panda.consumer9091.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Slf4j
@RefreshScope
public class HelloController {

    public static final String MESSAGE_CODE = "i get [%s] provider-8081";

    @Value("${spring.application.name}")
    public String name;

    @GetMapping("/{msg}")
    public String hello(@PathVariable("msg") String msg) {
        String message = String.format(MESSAGE_CODE, msg);
        log.info(name + " : " + message);
        return name + " : " + message;
    }
}
