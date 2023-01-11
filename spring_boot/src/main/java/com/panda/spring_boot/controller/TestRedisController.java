package com.panda.spring_boot.controller;

import com.panda.spring_boot.server.TestRedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class TestRedisController {

    @Autowired
    TestRedisServer testRedisServer;

    @GetMapping("/test-redis")
    public void testRedis() {
        testRedisServer.testRedis();
    }
}
