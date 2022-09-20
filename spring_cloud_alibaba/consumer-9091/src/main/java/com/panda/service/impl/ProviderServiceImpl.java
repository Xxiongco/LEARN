package com.panda.service.impl;

import com.panda.service.ProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String test(String msg) {
        log.info("error get provider service");
        return "error get provider service";
    }
}
