package com.panda.spring_boot.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import panda.test.DoSomthing;
import panda.test.MyCustomProperties;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private DoSomthing doSomthing;

    @GetMapping
    public void testRedis() {
        doSomthing.printProperties();
    }

    @PostMapping("/json")
    public void testJSONObject(@RequestBody JSONObject req) {

        JSONArray array = req.getJSONArray("paramIn");
        System.out.println(array);

    }

}
