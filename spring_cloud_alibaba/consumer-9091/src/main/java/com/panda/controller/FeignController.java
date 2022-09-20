package com.panda.controller;

import com.panda.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    public ProviderService providerService;


    @GetMapping("/{msg}")
    public String hello(@PathVariable("msg") String msg) {
        return providerService.test(msg);
    }
}
