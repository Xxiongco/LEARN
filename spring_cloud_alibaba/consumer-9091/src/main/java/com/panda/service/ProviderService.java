package com.panda.service;


import com.panda.service.impl.ProviderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 *  如果没有配置熔断，那么就算调用provider的时候发生了异常，那么也不会调用fallbck
 */
@FeignClient(value = "provider", fallback = ProviderServiceImpl.class)
public interface ProviderService {
    @GetMapping(value = "/hello/{msg}")
    String test(@PathVariable("msg") String msg);
}
