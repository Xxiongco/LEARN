package com.panda.service.impl;

import com.panda.DubboService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class DubboServiceImpl implements DubboService {
    @Override
    public String dubbo(String msg) {
        return "dubbo-provider-8083" + msg;
    }
}
