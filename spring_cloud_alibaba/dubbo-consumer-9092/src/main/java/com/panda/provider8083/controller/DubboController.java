package com.panda.provider8083.controller;

import com.panda.DubboService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class DubboController {

    @Reference
    private DubboService dubboService;

    @GetMapping("/{msg}")
    public String duboo(@PathVariable("msg") String msg) {

        return dubboService.dubbo(msg);
    }

}
