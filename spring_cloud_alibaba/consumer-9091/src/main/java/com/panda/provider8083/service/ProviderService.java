package com.panda.provider8083.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "provider")
public interface ProviderService {
    @GetMapping(value = "/hello/{msg}")
    String test(@PathVariable("msg") String msg);
}
