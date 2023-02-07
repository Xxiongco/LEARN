package com.panda.spring_boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import panda.test.DoSomthing;
import panda.test.MyCustomProperties;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private DoSomthing doSomthing;

    @GetMapping
    public void testRedis() {
        doSomthing.printProperties();
    }



}
