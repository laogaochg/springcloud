package com.example.prodive.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/6/28 15:26
 */
@RefreshScope
@RestController
public class TestController {

    @Value("${test-name}")
    private String from;

    @RequestMapping("/test-name")
    public String from() {
        return this.from;
    }

}